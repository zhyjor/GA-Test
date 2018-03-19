/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Model.Well;

/*
 * 计算井筒环空的压力，分为纯气柱段，高含气段，低含气段；
 * 《沁南煤储层渗透率动态变化效应及气井产能响应》——陶树
 *
 * @author 武浩
 */
public class WellboreP {

    /**
     * 方法中的参数含义
     */
    private double qg;//气体标况产量,10^4m3/d,注意单位！这里是万方
    private double D1;//套管内径,m
    private double D2;//油管外径,m
    private double hw;//储层中部深度,m
    private double pc;//井口套压,MPa
    private double L;//动液面深度,m
    private double Tavg;//井筒平均温度，K
    private double rRou = 0.5989;//煤层气相对密度

    /**
     * 混气液柱段压力修正系数
     */
    private double gcf(double vsg) {
        return Math.exp(0.03499 - 0.9631 * Math.pow(vsg, 0.67));
    }

    private double A(double D1, double D2) {//环空截面积
        return 3.14 * (Math.abs(D1 * D1 - D2 * D2) / 4.0);
    }

    private double vsg(double qg, double A) {//气体表观流速
        return qg / A / 86400;
    }

    /**
     * 纯气柱段的压力,压缩因子取为1
     */
    private double pg(double pc, double rRou, double L, double Tavg, double qg, double D1, double D2) {
        double s, n;
        s = 0.03415 * rRou * L / Tavg;
        double x = Math.abs(D1 * D1 - D2 * D2);
        double y = 1.324 * Math.pow(10, -10) * 0.014 * qg * qg * Tavg * Tavg / (Math.abs(D1 - D2)) / x / x * (Math.pow(2.7183, 2 * s) - 1);
        n = pc * pc * Math.pow(2.7183, 2 * s) + y;
        return Math.pow(n, 0.5);
    }

    /**
     * 这是本类最终的输出方法
     */
    public double P(double L, double D1, double D2, double pc, double qg, double Tavg, double hw) {//逐段计算混气液柱段压力，输入参数的含义见上面成员变量

        int n = (int) (hw - L);//液柱高度取整作为计算段数
        double p = pg(pc, rRou, L, Tavg, qg, D1, D2);//计算起点的压力，作为该段的平均压力
        double v = vsg(qg, A(D1, D2));//计算起点的流速
        double temp;
        double gcf;
        for (int i = 0; i < n; i++) {
            temp = p;
            gcf = gcf(v);
            p = p + 0.01 * gcf;//逐段累加
            v = v * temp / p;//换算表观流速
        }
        return p;
    }

    public static void main(String[] args) {
        WellboreP wp = new WellboreP();
        double L = 1000;
        double D1 = 0.1;
        double D2 = 0.05;
        double pc = 0.50;
        double qg =0.1;
        double Tavg = 300;
        double hw = 1000;
        double p;
        for (int i = 0; i < 11; i++) {
            p = wp.P(L, D1, D2, pc*i, qg, Tavg, hw);
            System.out.println(p);
        }

    }
}
