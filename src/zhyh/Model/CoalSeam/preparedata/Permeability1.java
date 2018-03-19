/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Model.CoalSeam.preparedata;

/**
 * 《三轴压缩岩石应变软化及渗透率演化的试验和数值模拟》——张春会 ； 岩石渗透率随岩石应力的变化过程
 *
 * @author 武浩
 */
public abstract class Permeability1 {

    private double deltaV;//体积应变
    public double k;//渗透率
    private double k0;//初始渗透率
    private double a, b;//渗透率与体积应变关系的常数k=a+b*deltaV;
    private double b3;//围压

    /**
     * 渗透率与体积应变及围压的关系拟合曲线方程，输入一个体积应变与围压，就能得到相应的渗透率
     */
    public abstract void k(double b3, double deltaV);

    /**
     * 某一围压下的初始渗透率
     */
    private void k0(double a, double b) {

    }

}
