/*
 * 地层模型的优化方程，用于Prepare_for_Opti类中
 */
package Opti.Functions;

import Data.MapStorage.StaticDataMap7;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 武浩
 */
public class opti_Coalseam_Part {

    static private Map<String, Double> Prmap;//各个点的地层压力
    static private Map<String, Double> Amap;//各个井的产能二项式系数A
    static private Map<String, Double> Bmap;//各个井的产能二项式系数B

    public opti_Coalseam_Part() {
        Prmap = StaticDataMap7.Pr;
        Amap = StaticDataMap7.EquaA;
        Bmap = StaticDataMap7.EquaB;
    }

    /**
     * 一个独立树结构井网的地层方程组，用于Prepare_for_Opti类中
     */
    public double[] Opti_CoalSeam_Part(List<String> name, double[] Q) {//输入一个子树全部井名称，点对应产量，
        int num = name.size();
        double y[] = new double[num];
        double pr;//地层压力
        double a;//产能方程系数
        double b;//产能方程系数
        String Name;//点名称
        double q;//产量
        for (int i = 0; i < num; i++) {//遍历各个点
            Name = name.get(i);
            q = Q[i];//井产量
            pr = Prmap.get(Name);//地层压力
            a = Amap.get(Name);// 产能方程系数
            b = Bmap.get(Name);// 产能方程系数
            y[i] = pr * pr - a * q - b * q * q;
        }
        return y;
    }
}
