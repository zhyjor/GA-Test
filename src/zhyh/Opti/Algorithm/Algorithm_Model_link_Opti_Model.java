/*
 * 
 *本类是本算法包的连接类，需要写入要计算的变量的取值范围以及适应度的判断方法
 */
package zhyh.Opti.Algorithm;

import zhyh.Opti.Functions.OptiAll;
import zhyh.Opti.Functions.Opti_Model_link_Algorrithm_Model;

/**
 *
 * @author 武浩
 */
public class Algorithm_Model_link_Opti_Model {

    private Opti_Model_link_Algorrithm_Model oa;
    public String target_Opti;//本次优化的膜牧目标
    public double[] Qmax;//目标点集的产量上限，用于GAdecoder类中
    public double[] Qmin;//目标点集的产量下限，用于GAdecoder类中
    public int num;

    public Algorithm_Model_link_Opti_Model() {
        oa = new Opti_Model_link_Algorrithm_Model();
        target_Opti = OptiAll.target_Opti;//本次优化的膜牧目标
        Qmax = OptiAll.Qmax;//目标点集的产量上限，用于GAdecoder类中
        Qmin = OptiAll.Qmin;//目标点集的产量下限，用于GAdecoder类中
        num = Qmax.length;
    }

    /**
     * 写入变量的适应度判断方法
     */
    public double fitness(double[] X) {
        return oa.fitness(X);
    }

}
