/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Model.Pipe;

/**
 * cole-brook式 主要目的Q(); 单位均为国际单位
 *
 * @author 武浩
 */
public class GuanQ {         //计算流量的方法类，·

    static double temp = 1;//暂时存储上次计算的q

    public double niandu(double p, double T) {    //气体动力粘度的计算,Pa.s;  (Pa,K)
        double nian;                        //温度为T时的粘度
        double nian0 = 1.027;               //温度为273.15K的动力粘度
        double P = p / 1000;
        if (P < 101.3) {                    //低压下，将粘度随压力的变化简化为线性
            nian0 = 1.027 * P / 101.3;
        } else if (P < 2026.5) {
            nian0 = 1.068 + 0.041 / 1900 * (P - 101.3);
        } else if (P < 6079.5) {
            nian0 = 1.22 + 0.15 / 4000 * (P - 2026.5);
        } else if (P < 10000.0) {
            nian0 = 1.42 + 0.2 / 4000 * (P - 6079.5);
        }
        nian = nian0 * Math.pow(T / 273.15, 0.76);       //粘度随温度的变化

        return nian * 0.00001;
    }

    public double Re(double q, double d, double dongliniandu) {//雷诺数，
        return 1.536 * Math.abs(q) * 0.58 / d / dongliniandu;
    }

    public double ColeBrook(double Re, double Ke, double d) {       //cole-brook式计算水力摩阻系数(雷诺数，当量粗糙度，管径)，适用于紊流区,迭代算摩阻系数

        double z = 0.0163;//初值
        double part1 = 0;//方程的左边
        double part2 = 1;//方程的右边
        while (Math.abs(part1 - part2) > 0.001) {
            part1 = 1 / Math.pow(z, 0.5);
            part2 = -2 * Math.log10(Ke / 3.7 / d + 2.51 / Re / Math.pow(z, 0.5));//方程右边部分
            z = 1 / part2 / part2;
        }

        return z;
    }

    /**
     * 计算管道流量的方法，这里不区分起点终点
     */
    public double Q(double gchang, double guanjing, double p1, double p2, double tq, double tz, double To, double ke) throws Exception//（m,m，Pa,Pa,K,K），计算流量的方法，这里的进口压力是油嘴后的回压。
    {
        double q = 1000;
        if (p1 == p2) {
            q = 0;
        } else {
            GuanT T = new GuanT();
            double a = T.a(tq, tz, To, gchang);
            GuanQ l = new GuanQ();
            double d = guanjing;                                             //管径,m
            double[] component = {0.9617, 0.0005, 0, 0, 0, 0, 0, 0, 0.0371, 0.0007};//气体组分
            double Pq = p1;//起点压力(Pa)
            double Pz = p2;//终点压力(Pa)
            if (p1 < p2) {
                Pq = p2;
                Pz = p1;
            }
            double Tpj;
            if (a == 0) {
                Tpj = tz;
            } else {
                Tpj = To + (tq - To) * (1 - Math.exp(-a * gchang)) / a / gchang;     //K,平均温度
            }
            double Ppj = 2 * (Pq + (Math.pow(Pz, 2)) / (Pq + Pz)) / 3;       //KPa，平均压力

            double Re = 100000;//雷诺数，迭代初值
            double mozuxishu = 1.0;//水力摩阻系数
            double Ke = ke;//粗糙度，m
            double niandu = niandu(Ppj, Tpj);//Pa.s
            while (Math.abs(temp - q) > 0.001) {//迭代求流量
                temp = q;
                Re = l.Re(q, d, niandu);//求雷诺数
                if (Re < 2000) {//求摩阻系数
                    mozuxishu = 64 / Re;
                } else if (Re < 4000) {
                    mozuxishu = 0.0025 * Math.pow(Re, 1 / 3);
                } else {
                    mozuxishu = l.ColeBrook(Re, Ke, d);
                }
                q = 0.03848 * Math.pow((Pq * Pq - Pz * Pz) * Math.pow(d, 5) / (0.58 * Tpj * gchang * mozuxishu), 0.5);//求流量   
            }
        }
        return q;//m3/s
    }
//
//    public static void main(String[] args) throws Exception {
//        GuanQ l = new GuanQ();
//        double q = l.Q(1000, 0.1, 600000, 400000, 293, 293, 293, 0.00005);
//        System.out.println(q);
//
//    }
}
