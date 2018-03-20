package Tool.Data_resource;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

/**
 * 构造函数输入要拟合的数据，唯一的方法输入要拟合的方程的次幂数，运行后a[]中就是各项系数，由低到高次幂 （完成）
 */
public class Nihe {

    public double a[];//拟合式的各项系数由低到高
    private double X[];//x座标
    private double Y[];//y座标

    public Nihe(double[] X, double[] Y) {
        this.X = X;
        this.Y = Y;
    }

    public void testLeastSquareMethodFromApache(int n) {
        WeightedObservedPoints obs = new WeightedObservedPoints();
        int x = X.length;
        for (int i = 0; i < x; i++) {
            obs.add(X[i], Y[i]);
        }

        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(n);
        a = fitter.fit(obs.toList());

    }

}
