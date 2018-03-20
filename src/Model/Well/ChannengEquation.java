/*
 * 
 */
package Model.Well;

/*
 * 二项式产能方程，Pr^2-Pwf^2=A*qg+Bqg^2 
 * 根据输入的测井资料，拟合出该井的产能方程。
 * 然后可进行流量与流压互算 （完成）
 * 单位均为现场数据单位
 * @author 武浩
 */
public class ChannengEquation {

    public double Pwf(double A, double B, double Pr, double qg) {//利用产量求井底流压，
        double a = A;
        double b = B;
        double pwf = Math.pow(Pr * Pr - a * qg - b * qg * qg, 0.5);
        return pwf;
    }

    public double qg(double A, double B, double Pr, double Pwf) {//利用井底流压求产量
        double a = A;
        double b = B;
        double y = Pr * Pr - Pwf * Pwf;
        double q = (-a + Math.pow(a * a + 4 * y * b, 0.5)) / 2 / b;
        return q;
    }

}
