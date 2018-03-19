/*
 * 本类主要是提供井产量上下限
 */
package zhyh.Model.CoalSeam.Qmax_Qmin;

import zhyh.Data.MapStorage.StaticDataMap7;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据井的生产压差限制推算其产量的限制，用的是二项式产能方程
 *
 * @author 武浩
 */
public class CoalSeam_Qmax_Qmin {

    static private Map<String, Double> Prmap;//各个点的地层压力
    static private Map<String, Double> Amap;//各个井的产能二项式系数A
    static private Map<String, Double> Bmap;//各个井的产能二项式系数B
    static private double Pmax;
    static private double Pmin;
    static private double Qmax;
    static private double Qmin;

    /**
     * 下面的通过运行本类方法获得
     */
    public Map<String, Double> Qmaxmap;//各个井的产量上限
    public Map<String, Double> Qminmap;//各个井的产量下限
    public Map<String, Double> Pmaxmap;//各个井的产量上限
    public Map<String, Double> Pminmap;//各个井的产量下限
    private Map<String, Integer> ID;//查询点的属性，0为井
    private CoalSeam_Pmax_Pmin cpp;

    public CoalSeam_Qmax_Qmin() {
        cpp = new CoalSeam_Pmax_Pmin();
        Prmap = StaticDataMap7.Pr;//各个点的地层压力
        Amap = StaticDataMap7.EquaA;//各个井的产能二项式系数A
        Bmap = StaticDataMap7.EquaB;//各个井的产能二项式系数B
        ID = StaticDataMap7.ID;
    }

    /**
     * 便利全部点，写入Map，用于Link_Coalseam_Model类中
     */
    protected void input_wellList(List<String> namelist) {
        Qmaxmap = new HashMap();
        Qminmap = new HashMap();
        Pmaxmap = new HashMap();
        Pminmap = new HashMap();
        for (String o : namelist) {
            if (ID.get(o) == 0) {
                input_wellname(o);
                Qmaxmap.put(o, Qmax);
                Qminmap.put(o, Qmin);
            }
        }
    }

    /**
     * 输入井名称，算出该井的产量上下限
     */
    private void input_wellname(String name) {
        cpp.input_wellname(name);
        CoalSeam_Qmax_Qmin.Pmax = cpp.Pmax;
        CoalSeam_Qmax_Qmin.Pmin = cpp.Pmin;
        Pmaxmap.put(name, Pmax);
        Pminmap.put(name, Pmin);
        double a = Amap.get(name);
        double b = Bmap.get(name);
        double pr = Prmap.get(name);
        double cmax = pr * pr - Pmin * Pmin;
        double cmin = pr * pr - Pmax * Pmax;
        Qmax = result(a, b, cmax);
        Qmin = result(a, b, cmin);
//        System.out.println("this well is：" + name + " ;a=" + a + " ;b=" + b + " ;Pr=" + pr + " ;Pmax=" + Pmax + " ;Pmin=" + Pmin + " ;Qmax=" + Qmax + " ;Qmin=" + Qmin);
    }

    /**
     * 解二项式方程，b*q^2+a*q=pr^2-pwf^2
     */
    private double result(double a, double b, double c) {
        double temp = a * a + 4 * b * c;
        if (temp < 0) {
            System.out.println("");
            System.out.println("CoalSeam_Qmax_Qmin：计算出错了!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("a="+a);
            System.out.println("b="+b);
            System.out.println("c="+c);
            System.out.println("temp="+temp);
            System.out.println("");
        }
        double x = (-a + Math.pow((temp), 0.5)) / 2.0 / b;
        return x;
    }

}
