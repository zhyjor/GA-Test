/*
 * 岩石强度参数等模型，用于Reaonable_WangZhiyin类中
 */
package Model.CoalSeam.Qmax_Qmin;

/*
 * 岩石强度退化参数 《考虑围压影响的岩石峰后应变软化力学模型》——张春会等 
 * 可用《软弱岩石峰后应变软化力学特性研究》——陆银龙，中的数据验证
 *
 * @author 武浩
 */
public class canyuPara {

    /**
     * 以下参数为实验获得
     */
    private double deltaBcu;//单轴时的强度退化量
    private double b3;//围压
    private double c;//岩石内聚力
    private double fai;//内摩擦角
    private double bc;//岩石单轴强度
    private double md = 0;//常数,实验确定，反映Xd与b3之间的关系；可以直接输入，或者计算获得。******

    /**
     * 以下参数由程序获得
     */
    private double Xd = 0;//峰后强度退化指数
    private double b1f;//围压b3下的单轴强度
    private double n;//常数
    private double k;//摩尔库伦准则中内摩擦角相关参数
    private double kr;//残余内摩擦角相关参数
    public double cr;//残余内聚力

    public void input(double b3, double md, double c, double deltaBcu, double fai, double bc) {//输入围压,md
        this.md = md;
        this.b3 = b3;
        this.c = c;
        this.fai = fai;
        this.deltaBcu = deltaBcu;
        this.bc = bc;
        Xd(b3);
        k();
        b1f();
        n();
        cr();
    }

    private void Xd(double b3) {//峰后强度退化指数与围压关系,输入围压即可
        Xd = Math.exp(-md * b3);
    }

    private void k() {//一个常数
        double x = Math.sin(fai / 180.0 * 3.1416);
        k = (1 + x) / (1 - x);
        kr = k;
    }

    private void n() {
        n = (b1f - b1f / bc * Xd * deltaBcu - k * b3) / (2 * c * Math.pow(k, 0.5));
    }

    private void cr() {
        cr = n * c;
    }

    private void b1f() {
        b1f = k * b3 + 2 * c * Math.pow(k, 0.5);
    }

    /**
     * @param md the md to set
     */
    public void setMd(double md) {
        this.md = md;
    }

}
