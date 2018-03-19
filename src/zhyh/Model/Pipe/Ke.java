/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Model.Pipe;

/*
 * 默认相对密度0.58，压缩因子为1
 * 由于实际生产中的管路参数与设计值并不相符，采取反算当量参数的方法来缩小与实际的误差，这里采用当量粗糙度的方法来计算
 * 方法中的公式是由colebrook式、基本流量方程（忽略高差）等推导而来 
 * 方法无视流向，但温度有方向
 */
public class Ke {

    public double Ke(double Q, double guanchang, double guanjing, double Pq, double Pz, double Tq, double Tz, double To) {//反推当量粗糙度(流量，管径，起点压力，终点压力，起点温度，终点温度(（m3/s,m,Pa,Pa,K,K）)
        if (Q == 0) {//修正流量
            System.out.println("流量为0,无法计算！");
        } else if (Q < 0) {
            Q = -Q;
        }
        GuanT T = new GuanT();
        double a = T.a(Tq, Tz, To, guanchang);
        GuanQ l = new GuanQ();
        double d = guanjing;                        //管径,m
        double[] component = {0.9617, 0.0005, 0, 0, 0, 0, 0, 0, 0.0371, 0.0007};//气体组分
        double temp;
        if (Pq < Pz) {//修正压力
            temp = Pq;
            Pq = Pz;
            Pz = temp;
        }
        double Tpj;
        if (a == 0) {
            Tpj = Tz;
        } else {
            Tpj = To + (Tq - To) * (1 - Math.exp(-a * guanchang)) / a / guanchang;//K,平均温度
        }
        double Ppj = 2 * (Pq + (Math.pow(Pz, 2)) / (Pq + Pz)) / 3;       //KPa，平均压力
        Bwrs ab = new Bwrs(component);
        ab.init(Ppj / 1000, Tpj);                     //求实际情况下的其他参数(KPa,K)
        double Ke;
        double niandu = l.niandu(Ppj, Tpj);//Pa.s
        double Re = l.Re(Q, d, niandu);//雷诺数
        double mozuxishu = (Pq * Pq - Pz * Pz) * d * d * d * 0.0034934341238784003 * 0.58 / ab.getZ() / Tpj / guanchang / Re / Re / niandu / niandu;
        Ke = 3.7 * d * (Math.pow(10, -0.5 / Math.pow(mozuxishu, 0.5)) - 2.51 / Re / Math.pow(mozuxishu, 0.5));
        return Ke;
    }
}
