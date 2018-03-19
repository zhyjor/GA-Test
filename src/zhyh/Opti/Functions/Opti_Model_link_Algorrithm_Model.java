/*
 *该类是是地层模型，管网模型以及智能算法结合的部分，只需要输入根节点即可
 */
package zhyh.Opti.Functions;

/**
 * （1）Prepare_for_Opti类中优化模型的智能算法化，主要是编写具体的适应度的算法，
 * （2）连接算法包Algorithm_Model_link_Opti_Model类使用
 *
 * @author 武浩
 */
public class Opti_Model_link_Algorrithm_Model {

    private Prepare_for_Opti pfo;

    public Opti_Model_link_Algorrithm_Model() {
        pfo = new Prepare_for_Opti();
    }

    /**
     * 不需要输入，不用管这个方法,返回的是适应度，供SouSuo类，与GA类调用
     */
    public double fitness(double[] X) {
        double fit = 1 / Difference(X);
        return fit;
    }

    /**
     * 这里计算的是y[i]的方差，然后求和，是适应度的算法，可以改进！
     */
    private double Difference(double[] X) {
        int num = X.length;
        double result = 0;
        double dff = 0;
        double y1[] = Curve1(X);
        double y2[] = Curve2(X);
        for (int i = 0; i < num; i++) {
            dff = (y1[i] - y2[i]);
            result = result + dff * dff;
        }
//        System.out.println("Opti_Model_link_Algorrithm_Model：：两个模型结果的Difference值为：" + result);
        return result;
    }

    /**
     * 曲线1，管网部分方程组
     */
    private double[] Curve1(double[] X) {//输入一个子树全部点名称，点对应产量
        double y[] = pfo.Y1(X);
        return y;
    }

    /**
     * 曲线2，地层部分方程组
     */
    private double[] Curve2(double[] X) {
        double y[] = pfo.Y2(X);
        return y;
    }

}
