/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Model.Pipe;

/**
 *
 * @author 武浩
 */
public class GuanPpj {

    /**
     * 管道平均压力
     */
    public double Ppj(double Pq, double Pz) {
        if (Pq < Pz) {
            double temp = Pq;
            Pq = Pz;
            Pz = temp;
        }
        double Ppj = 2.0 / 3.0 * (Pq + Pz * Pz / (Pq + Pz));
        return Ppj;
    }

}
