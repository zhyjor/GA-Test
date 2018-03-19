package zhyh.Model.CoalSeam.Qmax_Qmin;

/**
 * 《煤层气开采初期井底压力的合理控制范围研究》——王芝银 ; 根据岩石特性求合理生产压力区间
 *
 * @author 武浩
 */
public class ReasonableP_WangZhiyin {

    static public double Pmin;//压力下限(MPa)
    static private double Pmax;//压裂压力(MPa)
    static public double Pmid;//压力上限(MPa)
    static private double St;//拉应力(MPa)
    static private double PR;//地层压力(MPa)

    static private canyuPara cy = new canyuPara();
    static private double c;//内聚力(MPa)
    static private double fai;//内摩擦角（°）
    static private double cr;//残余内聚力(MPa)
    static private double fair;//残余内摩擦角（°）

    /**
     * 输入地层压力(MPa)，煤岩内聚力(MPa)，内摩擦角（°），残余内聚力(MPa)，残余内摩擦角（°），单轴抗压应力(MPa)，主要用这个！！！
     */
    public void input(double Pr, double c, double fai, double cr, double fair, double St) {
        this.cr = cr;
        this.fair = fair / 180.0 * 3.1415926;
        this.PR = Pr;
        this.c = c;
        this.fai = fai / 180.0 * 3.1415926;
        this.St = St;
        Pmin();
        Pmax();
        Pmid();
    }

    /**
     * 输入地层压力（MPa），md实验值，内聚力(MPa)，单轴抗压强度降(MPa)，内摩擦角(°)，单轴抗压强度(MPa)，单轴抗压应力(MPa)
     */
    public void input(double Pr, double md, double c, double deltaBcu, double fai, double bc, double St) {
        cy.input(Pr, md, c, deltaBcu, fai, bc);
        cr = cy.cr;
        fair = fai / 180.0 * 3.1415926;
        ReasonableP_WangZhiyin.PR = Pr;//绝对压力
        ReasonableP_WangZhiyin.c = c;
        ReasonableP_WangZhiyin.fai = ReasonableP_WangZhiyin.fair;
        ReasonableP_WangZhiyin.St = St;
        Pmin();
        Pmax();
        Pmid();
    }

    private void Pmin() {
        double const1 = cr * Math.cos(fair);
        double const2 = 1.0 - Math.sin(fair);
        Pmin = const1 / const2 * (const1 / 4.0 / St / const2 - 1.0);
        if (Pmin >= PR || Pmin <= 0) {
            Pmin = 0;
        }

    }

    private void Pmid() {
        Pmid = (PR * (1 - Math.sin(fai)) - 2.0 * c * Math.cos(fai)) / (1.0 - Math.sin(fai));
        if (Pmid <= 0) {
            Pmid = Math.min(PR, Pmax);
        }
        if (Pmid > PR || Pmid > Pmax) {
            Pmid = Math.min(PR, Pmax);
        }
    }

    private void Pmax() {
        Pmax = (PR * (1.0 - Math.sin(fai)) - c * Math.cos(fai)) / (1.0 - Math.sin(fai));
        if (Pmax <= 0 || Pmax <= Pmin) {
            Pmax = PR;
        }
    }

}
