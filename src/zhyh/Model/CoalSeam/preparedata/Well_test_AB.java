/*
 * 改类是利用测井资料拟合IPR曲线中的A、B，如果已知生产压差与产量的关系
*
 */
package zhyh.Model.CoalSeam.preparedata;

import zhyh.Tool.Data_resource.Nihe;

/**
 * 用于拟合二项式产能方程中的系数A、B，Pr^2-Pwf^2=AX+BX^2（完成，待改进）
 *
 * @author 武浩
 */
public class Well_test_AB {

    private double A;//一次系数
    private double B;//二次系数
    private double Pr;//地层压力
    /**
     * Y=AX+BX^2,拟合得到产能方程
     */
    private double X[];//X=qg
    private double Y[];//Y=Pr^2-Pwf^2

    /**
     * 如果已知地层压力，拟合方法
     */
    public void input(double Pr, double[] Pwf, double[] qg) {
        this.Pr = Pr;
        this.X = qg;
        this.Y = Y(Pr, Pwf, qg);
        nihe(X, Y);
    }

    /**
     * 构造拟合方程的Y座标，已知地层压力
     */
    private double[] Y(double Pr, double[] Pwf, double[] qg) {
        int n = Pwf.length;
        double[] Y = new double[n];
        double y = Pr * Pr;
        for (int i = 0; i < n; i++) {
            Y[i] = (y - Pwf[i] * Pwf[i]) / qg[i];

            System.out.println("P=" + Pwf[i] + ",Y=" + Y[i] + ",X=" + X[i]);

        }
        return Y;
    }

    /**
     * 拟合获得A/B
     */
    private void nihe(double[] qg, double Y[]) {
        Nihe nihe = new Nihe(qg, Y);
        nihe.testLeastSquareMethodFromApache(1);
        double a[] = nihe.a;
        this.A = a[0];
        this.B = a[1];
        System.out.println("产能方程为：Pr^2-Pwf^2=" + getA() + "qg+" + getB() + "qg^2");
    }

    /**
     * @return the A
     */
    public double getA() {
        return A;
    }

    /**
     * @return the B
     */
    public double getB() {
        return B;
    }

    /**
     * @return the Pr
     */
    public double getPr() {
        return Pr;
    }
}
