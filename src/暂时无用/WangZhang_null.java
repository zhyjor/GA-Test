package Model.CoalSeam;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Yueshu;
//
///**
// * 汪-张渗透率模型，考虑气体解吸引起的基质收缩效应和孔隙压力对储层应力的影响的渗透率计算模型。来源《煤层应力对裂隙渗透率的影响》。
// *
// * @author 武浩
// */
//public class WangZhang extends Data {
//
//    private double db() {//求地层应力变化量
//        double db;
//        double tempt1;
//        double tempt2;
//        tempt1 = 1 - 2 * posong;
//        tempt2 = 1 + B * P;
//        db = -posong / (1 + posong) / tempt1 * dp + E / 3 / tempt1 * dp / tempt2 / tempt2;
//        return db;
//    }
//
//    /**
//     * 裂隙体积压缩系数
//     */
//    private double K() {
//        double k;
//        k = ko * Math.exp(-3 * cf * db());
//        return k;
//    }
//
//
//    public void value() {//这里给从Data类里继承的参数赋值
//        /**
//         * ************************************
//         */
//    }
//
//    public static void main(String[] args) {
//        WangZhang W = new WangZhang();
//
//    }
//}
