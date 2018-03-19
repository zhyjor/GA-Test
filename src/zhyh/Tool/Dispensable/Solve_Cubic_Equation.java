/*
 不是本软件必须的包
 * 本类主要用生产历史数据来拟合推算产能方程，用的是三元一次方程的解法，需要自行筛选数据。
 * 利用的是井底流压Pwf，与产量Q。单位分别是MPa与m^3/d；
 * 处理生产历史数据用的，非模型必须的包！！！
 */
package zhyh.Tool.Dispensable;

import zhyh.Tool.Data_resource.Nihe;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 解一元三次方程组:X1 - a*X2 - a^2*X3 = C （a=a1,a2,a3 ; C=C1,C2,C3）
 *
 * @author 武浩
 */
public class Solve_Cubic_Equation {

    private double a[] = new double[3];
    private double c[] = new double[3];
    private double X[] = new double[3];

    /**
     * 解一次三元方程组用
     */
    private void X() {
        X[2] = 1 / (a[0] - a[2]) * ((c[0] - c[1]) / (a[1] - a[0]) - (c[1] - c[2]) / (a[2] - a[1]));
        X[1] = (a[1] + a[2]) * (c[1] - c[0]) / (a[0] - a[2]) / (a[1] - a[0]) + (a[1] + a[0]) * (c[1] - c[2]) / (a[2] - a[1]) / (a[0] - a[2]);
        X[0] = c[0] + a[0] * X[1] + a[0] * a[0] * X[2];
    }

    private Workbook wb;//用于拟合的数据来源Excel
    private Sheet sheet;//用于拟合的数据来源Sheet
    private double[] q;//产量
    private double[] pwf;//井底流压

    private double Pr;//地层压力
    private double A;//产能方程系数A
    private double B;//产能方程系数B

    private Cell[] Q;//存储数据表中的流量数据
    private Cell[] P;//存储数据表中的压力数据

    private int hangshu;//Excel中数据行数，包括了标题行
    private double Xmin;//拟合曲线的区间最小值
    private double Xmax;//拟合曲线的区间最大值
    private String[] sheetname;//excel中工作表名称列表
    private List<Double> temp;//筛选数据，去除表中空的行

    /**
     * 构造方法
     */
    public Solve_Cubic_Equation() throws IOException, BiffException {
        wb = Workbook.getWorkbook(new File("D:\\360网盘\\百度云同步盘\\数据拟合用.xls"));
        sheetname = wb.getSheetNames();
        sheet = wb.getSheet(0);
        temp = new ArrayList();
        Q = sheet.getColumn(0);
        P = sheet.getColumn(1);
        q = new double[3];
        pwf = new double[3];
        hangshu = Q.length;
        Xmin = Double.parseDouble(Q[1].getContents());
        Xmax = Xmin;
    }
    /**
     * 拟合工具，最小二乘法
     */
    private Nihe nihe;

    /**
     * 解得地层压力，产能方程系数，该方法要在Output()中最后运行
     */
    private void Solve() throws IOException, BiffException {

        for (int i = 0; i < 3; i++) {
            a[i] = q[i];
            c[i] = pwf[i] * pwf[i];
        }
        X();//将a/c带入，解方程
        Pr = Math.pow(X[0], 0.5);
        A = X[1];
        B = X[2];
    }

    private double xishu[] = new double[2];//曲线系数集合，（0次，1次）

    /**
     * 将数据一次曲线拟合
     */
    private void nihe() {

        double x[] = new double[hangshu - 1];
        double y[] = new double[hangshu - 1];

        for (int i = 1; i < hangshu; i++) {
            x[i - 1] = Double.parseDouble(Q[i].getContents());
            if (x[i - 1] > Xmax) {//模拟区间
                Xmax = x[i - 1];
            } else if (x[i - 1] < Xmin) {
                Xmin = x[i - 1];
            }
            y[i - 1] = Double.parseDouble(P[i].getContents());
        }
        nihe = new Nihe(x, y);//输入需要拟合的数据
        nihe.testLeastSquareMethodFromApache(1);//拟合成一次曲线
        xishu = nihe.a;//得到曲线的系数集合
    }

    /**
     * 拟合得到的曲线，用于Output()中，必须在nihe()运行之后
     */
    private double quxian(double x) {
        double y = xishu[0] + xishu[1] * x;
        return y;
    }
    private WritableWorkbook wwb = Workbook.createWorkbook(new File("D:\\360网盘\\百度云同步盘\\ABPr" + ".xls"));
    private WritableSheet ws = wwb.createSheet("ABPr", 0);

    public void Output() throws IOException, BiffException, WriteException {
        nihe();

        q[0] = Xmin;
        pwf[0] = quxian(q[0]);
        q[1] = (Xmin + Xmax) / 2;
        pwf[1] = quxian(q[1]);
        q[2] = Xmax;
        pwf[2] = quxian(q[2]);

        ws.addCell(new Label(0, 0, "A"));
        ws.addCell(new Label(1, 0, "B"));
        ws.addCell(new Label(2, 0, "Pr"));

        Solve();//得到Pr，A，B
        ws.addCell(new jxl.write.Number(0, 1, A));
        ws.addCell(new jxl.write.Number(1, 1, B));
        ws.addCell(new jxl.write.Number(2, 1, Pr));

        wwb.write();
        wwb.close();
    }

    public static void main(String[] args) throws IOException, BiffException, WriteException {
        Solve_Cubic_Equation eee = new Solve_Cubic_Equation();
        eee.Output();
    }
}
