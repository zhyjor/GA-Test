/*
 * 这里是将地层模型，管网模型中数据进行整理，统一，为优化做准备,用于Opti_Starter中
 *因为要带入算法中时，两个模型中井的名称顺序以及各个井的产量上下限顺序必须相同，数量必须相等
 */
package zhyh.Opti.Functions;

import java.util.List;

/**
 * 将地层模型（opti_Coalseam_Part类），管网模型（opti_Pipenet_Part类）合为一个完整优化模型
 *
 * @author 武浩
 */
public class Prepare_for_Opti {

    private opti_Coalseam_Part ocp;
    private opti_Pipenet_Part opp;

    public Prepare_for_Opti() {
        ocp = new opti_Coalseam_Part();
        opp = new opti_Pipenet_Part();
    }

    /**
     * 管网部分方程组
     */
    public double[] Y1(double[] X) {
        double[] y1 = opp.functions_part1(OptiAll.target_Opti, X);
        return y1;
    }

    /**
     * 地层部分方程组
     */
    public double[] Y2(double[] X) {
        double y2[] = ocp.Opti_CoalSeam_Part(OptiAll.well_in_childtree, X);
        return y2;
    }

}
