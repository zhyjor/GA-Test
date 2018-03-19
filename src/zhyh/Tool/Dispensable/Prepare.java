/*
 *数据挖掘功能类，本类主要利用生产历史数据与模型推算井底流压，
不是本软件必须的包！！！！！
 */
package zhyh.Tool.Dispensable;

import zhyh.Model.Well.WellboreP;
import java.io.File;
import java.io.IOException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 数据表中有空着的部分，将其填满
 *
 * @author 武浩
 */
public class Prepare {

    private Workbook wb;//读取Excel
    private Sheet sheet;//读取Sheet

    private WritableWorkbook wwb;//输出Excel
    private WritableSheet ws;//输出Sheet
    private int titleHang = 2;//标题行行数
    /**
     * 项目名，读取的
     */
    private String Name1 = "井号";
    private String Name2 = "动液面（m）";
    private String Name3 = "套压(Mpa)";
    private String Name4 = "煤层顶板（m）";
    private String Name5 = "产气量(m3)";
    /**
     * 名称所在列数，上面的项目编号
     */
    private int Num[] = new int[5];

    /**
     * 数据存储
     */
    private String[] WellNum;//井号
    private double[] Hw;//动液面深度
    private double[] Pcase;//套压
    private double[] Hcoalseam;//煤层顶板高度
    private double[] Qg;//煤层气产量
    private double[] Pwf;//井底流压

    /**
     * 输出表项目
     */
    private String name1 = "井号";
    private String name2 = "井底压力";
    private String name3 = "产量";

    public static void main(String[] args) throws IOException, BiffException, WriteException {
        new Prepare().prepare();
    }

    /**
     * 
     */
    private void prepare() throws IOException, BiffException, WriteException {

        wb = Workbook.getWorkbook(new File("D:\\360网盘\\百度云同步盘\\生产数据ww" + ".xls"));
        sheet = wb.getSheet("排采数据表");

        wwb = Workbook.createWorkbook(new File("D:\\360网盘\\百度云同步盘\\www.xls"));
        ws = wwb.createSheet("处理后的数据", 0);

        jxl.Cell[] content = sheet.getColumn(8);//统计行数用的
        jxl.Cell[] title = sheet.getRow(2);//标题行

        int lieshu = title.length;//总列数
        int hangshu = content.length;//总行数
        for (int i = 0; i < lieshu; i++) {//赋值语句
            if (title[i].getContents().equals(Name1)) {
                Num[0] = i;
            } else if (title[i].getContents().equals(Name2)) {
                Num[1] = i;
            } else if (title[i].getContents().equals(Name3)) {
                Num[2] = i;
            } else if (title[i].getContents().equals(Name4)) {
                Num[3] = i;
            } else if (title[i].getContents().equals(Name5)) {
                Num[4] = i;
            }
        }

        jxl.Cell[] wellNum = new jxl.Cell[hangshu];//井号
        wellNum = sheet.getColumn(Num[0]);
        jxl.Cell[] hw = new jxl.Cell[hangshu];//动液面深度
        hw = sheet.getColumn(Num[1]);
        jxl.Cell[] pcase = new jxl.Cell[hangshu];//套压
        pcase = sheet.getColumn(Num[2]);
        jxl.Cell[] hcoalseam = new jxl.Cell[hangshu];//煤层顶板高度
        hcoalseam = sheet.getColumn(Num[3]);
        jxl.Cell[] qg = new jxl.Cell[hangshu];//煤层气产量
        qg = sheet.getColumn(Num[4]);

        WellboreP wp = new WellboreP();//计算井底压力的类
        
        double L;//动液面深度
        double D1;//套管内径
        double D2;//油管外径
        double pc;//套压
        double q;//气体流量
        double T;//井筒温度
        double h;//煤层深度
        double pwf;//井底流压

        double temp1 = 0;
        double temp2 = 0;

        String con;//内容

        ws.addCell(new Label(0, 0, "井号"));
        ws.addCell(new Label(1, 0, "煤层深度"));
        ws.addCell(new Label(2, 0, "动液面"));
        ws.addCell(new Label(3, 0, "产量"));
        ws.addCell(new Label(4, 0, "井底流压"));

        for (int i = titleHang + 1; i < hangshu; i++) {

            ws.addCell(new Label(0, i - titleHang, wellNum[i].getContents()));//输出井号

            try {
                ws.addCell(new jxl.write.Number(1, i - titleHang, Double.parseDouble(hcoalseam[i].getContents())));//输出煤层深度
                ws.addCell(new jxl.write.Number(2, i - titleHang, Double.parseDouble(hw[i].getContents())));//输出动液面
                ws.addCell(new jxl.write.Number(3, i - titleHang, Double.parseDouble(qg[i].getContents())));//输出产量
                L = Double.parseDouble(hw[i].getContents());//动液面,m
                D1 = 0.1397;//套管内径，m
                D2 = 0.073;//油管外径,m
                pc = Double.parseDouble(pcase[i].getContents());//套压，MPa
                q = Double.parseDouble(qg[i].getContents()) / 10000.0;//产量，万方/d
                T = 295;//井筒平均温度
                h = Double.parseDouble(hcoalseam[i].getContents());//煤层深度,m
                pwf = wp.P(L, D1, D2, pc, q, T, h);//计算井底流压，MPa

                ws.addCell(new jxl.write.Number(4, i - titleHang, pwf));//输出井底流压

            } catch (Exception e) {

            }
        }

        wwb.write();
        wwb.close();
    }
}
