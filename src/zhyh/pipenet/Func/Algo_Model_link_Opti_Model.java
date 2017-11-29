/*
 * 
 *本类是本算法包的连接类，需要写入要计算的变量的取值范围以及适应度的判断方法
 */
package zhyh.pipenet.Func;


import zhyh.pipenet.test.Opti_Pipenet;


/**
 *
 * @author zhyh
 */
public class Algo_Model_link_Opti_Model {

    private Opti_Model_Link_Algo_Model oa;
    public String target_Opti;//本次优化的膜牧目标
    public double[] Qmax;//目标点集的产量上限，用于GAdecoder类中
    public double[] Qmin;//目标点集的产量下限，用于GAdecoder类中
    public int num;

    public Algo_Model_link_Opti_Model() {
        oa = new Opti_Model_Link_Algo_Model();
        target_Opti = Opti_Pipenet.target_Opti;//本次优化的膜牧目标
        Qmax = Opti_Pipenet.Qmax;//目标点集的产量上限，用于GAdecoder类中
        Qmin = Opti_Pipenet.Qmin;//目标点集的产量下限，用于GAdecoder类中
        num = Qmax.length;
    }

    /**
     * 写入变量的适应度判断方法
     */
    public double fitness(double[] X) {
        return oa.fitness(X);
    }
}
