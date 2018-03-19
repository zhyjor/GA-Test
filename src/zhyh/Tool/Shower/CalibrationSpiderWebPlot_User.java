package zhyh.Tool.Shower;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RectangleEdge;

/**
 * 刻度雷达图示例
 *
 * @version 1.0
 */
public class CalibrationSpiderWebPlot_User extends JFrame {

    private static final long serialVersionUID = 1L;
    private CategoryDataset cd;

    /**
     * 创建DEMO实例
     *
     * @param s
     */
    public CalibrationSpiderWebPlot_User(String s, CategoryDataset cd) {
        super(s);
        this.cd = cd;
        JPanel jpanel = createDemoPanel();
        jpanel.setPreferredSize(new Dimension(1250, 900));
        setContentPane(jpanel);

    }

    /**
     * 构造数据集
     *
     * @return
     */
    private CategoryDataset createDataset() {

        return cd;
    }

    /**
     * 创建图表
     *
     * @param categorydataset
     * @return
     */
    public JFreeChart createChart() {
        CategoryDataset categorydataset = createDataset();
        CalibrationSpiderWebPlot spiderwebplot = new CalibrationSpiderWebPlot(categorydataset);
//        spiderwebplot.setStartAngle(90D);//刻度轴方向:54度,默认是90度即垂直于水平面
        spiderwebplot.setInteriorGap(0.15D);
        spiderwebplot.setToolTipGenerator(new StandardCategoryToolTipGenerator());//标准分类提示器
//是否画环
        spiderwebplot.setDrawRing(true);
//刻度最大值
        spiderwebplot.setMaxValue(100);
//刻度数
        spiderwebplot.setTicks(5);
//刻度前缀
//        spiderwebplot.setLablePrefix("%");
//刻度后缀
        spiderwebplot.setLableSuffix("%");
        JFreeChart jfreechart = new JFreeChart("效率蛛网评价图（%）", TextTitle.DEFAULT_FONT, spiderwebplot, false);
        jfreechart.setBackgroundPaint(Color.lightGray); //设置背景颜色
        LegendTitle legendtitle = new LegendTitle(spiderwebplot);
        legendtitle.setPosition(RectangleEdge.BOTTOM); //X说明所在的位置
        jfreechart.addSubtitle(legendtitle);

        return jfreechart;
    }

    /**
     * 创建面板
     *
     * @return
     */
    @SuppressWarnings("unused")
    public JPanel createDemoPanel() {

        JFreeChart jfreechart = createChart();
        return new ChartPanel(jfreechart);
    }

    public void saveAsFile(String outputPath, int weight, int height) {
        FileOutputStream out = null;
        try {
            File outFile = new File(outputPath);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(outputPath);

// 保存为PNG
//            ChartUtilities.writeChartAsPNG(out, createChart(), weight, height);
// 保存为JPEG
            ChartUtilities.writeChartAsJPEG(out, createChart(), weight, height);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
// do nothing
                }
            }
        }
    }

}
