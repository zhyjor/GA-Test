/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.CoalSeam.preparedata;

/**
 * 地层渗透率随地层压力变化模型 ； 《沁南煤储层渗透率动态变化效应及气井产能响应》——陶树
 *
 * @author 武浩
 */
public class Permeability2 {

    private double kpj;//压力降至pj时的渗透率
    private double k0;//初始渗透率
    private double niu;//泊松比
    private double deltaVmax;//压力无限增大时最大的体积应变
    private double deltaV;//体积应变
    private double p50;//体积应变达到deltaVmax的一半时的压力
    private double p0;//初始储层压力
    private double pj;//储层压力
    private double fai0;//初始有效孔隙度

    protected void kpj() {

        double n;
        n = niu * 2 * deltaVmax / (1 + 2 * niu) / fai0 * (p0 / (p0 + p50) - pj / (pj + p50)) + 1;
        kpj = n * n * n * k0;

    }

}
