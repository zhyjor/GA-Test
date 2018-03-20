/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Well;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 武浩
 */
public abstract class Well {

    private static Map<String, Double> Prmap;//地层压力
    private static Map<String, Double> Pj2map;//井口压力,二项式计算
    private static Map<String, Double> Pg2map;//井口压力，管网水力计算
    private static Map<String, Double> Amap;//二项式产能方程系数A
    private static Map<String, Double> Bmap;//二项式产能方程系数B
    private static Map<String, Double> Qmap;//井口流量
    private static Map<String, Double> Qmaxmap;//井口流量
    private static Map<String, Double> calcuPj2map;//井口压力
    /**
     * Pr^2-Pw^2=AQ+BQ^2
     */
    private String name;//井的名称
    private double A;//产能二项式，系数A
    private double B;//产能二项式，系数B
    private double Pr;//地层压力
    private double Pwf;//井底压力
    private double Pmb;//井口套压,气嘴前
    private double Pma;// 气嘴后压力
    private double Qmax;// 产量上限
    /**
     * 测井数据，拟合用
     */
    private List<Double> Plist;//测井P数据
    private List<Double> Qlist;//测井P数据

    /**
     * 构造方法
     */
    public Well() {
        this.Prmap = new HashMap();
        this.Bmap = new HashMap();
        this.Amap = new HashMap();
        this.Pj2map = new HashMap();
        this.Qmap = new HashMap();
        this.Qmaxmap = new HashMap();
        this.Plist = new ArrayList();
        this.Qlist = new ArrayList();
    }

    /**
     * 给上述变量赋值
     */
    abstract public void value();

    /**
     * 通过二项式产能方程计算井口压力平方
     */
    private double Pj2(double Pwf, double A, double B, double Q) {
        return Pwf * Pwf - A * Q - B * Q * Q;
    }

}
