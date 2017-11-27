/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package 暂时无用;

/**
 * 产水量，《煤层气赋存——传导机制及气井产水方程推导》——张建光
 *
 * @author 武浩
 */
public abstract class Qw {

    public double Bw;//地层水体积系数
    private double fai;//流动孔隙度
    private double Vwt;
    private double Vwp;
    private double alfa;//常数
    private double krw;//水相相对渗透率
    private double S;//表皮系数
    private double k;//渗透率
    private double re;//供气半径
    private double rw;//井径
    private double miuw;//水的粘度
    private double deltaP;//生产压差
    private double n0;//生产井数
    private double V;//煤藏体积
    private double h;//煤层厚度
    private double Swi;//束缚水饱和度
    private double Siw;//两相渗流初始含水饱和度
    private double a;//相对渗透率形态参数，常数
    private double b;//相对渗透率形态参数，常数
    private double c;//积分常数
    private double N;
    private double A;
    private double B;
    private double qi;//初始产水量
    private double t;//时间
    private double qw;//产水量
    private double Di;//初始递减率
    private double Dt;//递减率

    public abstract void value();//给上面的参数赋值

    private void alfa() {
        alfa = 0.001842 * k * h * deltaP * n0 / Bw / miuw / Math.log(re / rw + S);
    }

    private void N() {
        N = b / (1 - b);
    }

    private void A() {
        A = Math.pow(c, N) * alfa * a;
    }

    private void B() {
        B = (1 - b) * alfa * Bw * a / V / fai / c;
    }

    private void Di() {
        Di = -B * N;
    }

    private void Dt() {
        Dt = -B * N / (1 + B * t);
    }

    private void qw() {
        qw = qi * (1 + Di * t / N) * N;
    }

}
