/*
 * 图像显示功能
 */
package Background_functions;

import Tool.Data_resource.DBcontroller;
import Tool.Shower.CalibrationSpiderWebPlot_Auxiliary;
import Tool.Shower.CalibrationSpiderWebPlot_User;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author 武浩
 */
public class Key5_Shower {

    public String output_rath = "d:/JfreeChart/煤层气田集输系统匹配性评价图.png";//图片保存路径
    public int picture_height = 700;//图像高度
    public int pictur_width = 900;//图像宽度
    public String picturn_title = "煤层气田一体化综合评价图";//弹窗的标题
    public boolean save = false;//是否输出图片并保存
    public List<String> membername;//评价对象——井、或者管道等等
    public String[] groupname;//同时显示几组对比数据，每组的名称
    public List<Double>[] data;//每组数据的具体参数
    public DBcontroller db = new DBcontroller();
    private String filename;

    /**
     * 构造方法，输入弹窗的名字，输入要对比哪几项参数，起个名字比如：String []item_name = {井匹配性，井的与管网匹配性};
     */
    public Key5_Shower(String[] item_name) {
        int num = item_name.length;
        data = new List[num];
        membername = new ArrayList();
        groupname = item_name;
    }

    /**
     * 显示方法，显示全部地区的数据
     *
     * @param args
     */
    public void estimate_show() {

        CalibrationSpiderWebPlot_Auxiliary ca = new CalibrationSpiderWebPlot_Auxiliary();

        CalibrationSpiderWebPlot_User calibrationSpiderWebPlotDemo1 = new CalibrationSpiderWebPlot_User(picturn_title, ca.Dataset(membername, groupname, data));

        calibrationSpiderWebPlotDemo1.pack();//自适应尺寸
        calibrationSpiderWebPlotDemo1.setDefaultCloseOperation(CalibrationSpiderWebPlot_User.DISPOSE_ON_CLOSE);//关窗
        RefineryUtilities.centerFrameOnScreen(calibrationSpiderWebPlotDemo1);
        calibrationSpiderWebPlotDemo1.setVisible(true);//图像显示与否
        if (save) {//图片输出保存方法，png
            calibrationSpiderWebPlotDemo1.saveAsFile(output_rath, pictur_width, picture_height);
        }
    }

    /**
     * 显示方法,限定地区，数据库中的region列
     *
     * @param args
     */
    public void estimate_show(String region) {
        CalibrationSpiderWebPlot_Auxiliary ca = new CalibrationSpiderWebPlot_Auxiliary(filename, region);
        if (region.equals("")) {
            ca = new CalibrationSpiderWebPlot_Auxiliary();
        }
        CalibrationSpiderWebPlot_User calibrationSpiderWebPlotDemo1 = new CalibrationSpiderWebPlot_User(picturn_title, ca.Dataset(membername, groupname, data));

        calibrationSpiderWebPlotDemo1.pack();//自适应尺寸
        calibrationSpiderWebPlotDemo1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关窗则程序中断
        RefineryUtilities.centerFrameOnScreen(calibrationSpiderWebPlotDemo1);
        calibrationSpiderWebPlotDemo1.setVisible(true);//图像显示与否
        if (save) {//图片输出保存方法，png
            calibrationSpiderWebPlotDemo1.saveAsFile(output_rath, pictur_width, picture_height);
        }
    }

    /**
     * 读取数据库数据,最后一项是各项数据缩放比例,图像默认显示最大值为100
     */
    public void dataRescource(String filename_DBase, String[] liename, double[] ratio) {
        this.filename = filename_DBase;
        ResultSet result = db.getFile(filename_DBase);
        int lienum = liename.length;
        List<Double>[] data = new ArrayList[lienum];
        List<Double> temp = new ArrayList();

        for (int i = 0; i < lienum; i++) {
            temp = db.ReadDouble(result, liename[i]);
            int num = temp.size();
            data[i] = new ArrayList(num);
            for (int j = 0; j < num; j++) {
                data[i].add(temp.get(j) * ratio[i]);
            }
        }
        this.data = data;
        List<String> name;
        if (filename_DBase.equals("Pipeline")) {
            List<String> start = db.ReadString(result, "StartPointName");
            List<String> end = db.ReadString(result, "EndPointName");
            int num = end.size();
            name = new ArrayList(num);
            for (int i = 0; i < num; i++) {
                name.add(start.get(i) + " => " + end.get(i));
            }
        } else {
            name = db.ReadString(result, "Name");
        }
        this.membername = name;
    }

    public static void main(String[] args) {
//        String[] name = {"井的节点匹配性", "井的结构匹配性"};
//        String data[] = {"Well_Matching", "Well_Pipenet_Matching"};
//        double[] ratio = {100.0, 100.0};
//        
//        String[] name = {"排水设备的流压匹配性"};
//        String data[] = {"EquipmentRunningEfficiency"};
//        double[] ratio = {100.0};
//        String[] name = {"实际管网输送效率", "优化后管网输送效率"};
//        String data[] = {"TransportEfficiency", "Opti_Efficiency"};
//        double[] ratio = {100.0, 100.0};
        String[] name = {"实际管网利用率"};
        String data[] = {"UtilizationEfficiency"};
        double[] ratio = {100.0};

        Key5_Shower key = new Key5_Shower(name);
        key.dataRescource("Pipeline", data, ratio);
//        key.estimate_show();
        key.estimate_show("");//该方法与上面一样，只在需要单独显示某一地区的单位数据时使用,region=""时默认显示全部
    }
}
