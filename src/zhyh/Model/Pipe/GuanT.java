/*
 * 该包中包括了：
 1.计算管道中析出水的方法
 2.计算管道流量的方法
 3.反算管道粗糙度以及当量管径的方法等；
 */
package zhyh.Model.Pipe;

/**
 * 计算管道沿线温度的变化，Tx()为x处的温度，a()为温降公式中的参数 ——单位均为国际单位
 *
 * @author 武浩
 */
public class GuanT {

    public double a(double Tq, double Tz, double To, double guanchang) {//利用输气管温度分布公式，反推全程管道平均系数a，（起点温度，终点温度，环境温度，管长）
        double a;
        if (Math.abs(Tq - To) <= Math.abs(Tz - To)) {
            a = 0;
        } else {
            a = Math.log(Math.abs(Tq - To) / Math.abs(Tz - To)) / guanchang;
        }
        return a;
    }

    /**
     * 沿着气体流动方向管道各处的平均温度值数组
     */
    public double[] Tpj(double Tq, double Tz, double To, double guanchang, int duanshu) {//（起点温度，终点温度，环境温度，管长，步长）
   
        double Tx[];//任一点处的温度
        double Tpj[] = new double[duanshu];
        double L = guanchang / duanshu;//步长
        double a;//系数
        a = a(Tq, Tz, To, guanchang);
        Tx = new double[duanshu + 1];
        double x;
        int dianshu = duanshu + 1;
        for (int i = 0; i < dianshu; i++) {//求各个点的温度
            x = L * i;
            Tx[i] = To + (Tq - To) * Math.exp(-a * x);
        }
        for (int i = 0; i < duanshu; i++) {//求每段的平均值
            Tpj[i] = Tx[i] + (Tx[i + 1] - Tx[i]) * 0.666666667;
        }
        return Tpj;
    }

}
