/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Model.CoalSeam.preparedata;

import zhyh.Data.MapStorage.StaticDataMap7;

/**
 * 由流量计算井底流压
 *
 * @author 武浩
 */
public class Best_Pwf {

    private double a;
    private double b;
    private double Pr;

    /**
     * 输入井的名称，井的流量，返回值为境地流压
     */
    public double pwf(String name, double q) {
        a = StaticDataMap7.EquaA.get(name);
        b = StaticDataMap7.EquaB.get(name);
        Pr = StaticDataMap7.Pr.get(name);
        double x = Math.pow((-b * q * q - a * q + Pr * Pr), 0.5);
        return x;
    }
}
