/*
 * 暂时无用
 */
package 暂时无用;

/**
 * 《煤层气开采初期井底压力的合理控制范围研究》——王芝银 ; 
 *
 * @author 武浩
 */
public class ReasonableP {

    private double rd;
    private double br;
    private double b3;
    private double b1;
    private double b;
    private double nd;
    private double bu;
    private double c;
    private double k;
    private double bc;

    private void br() {
        bc = 2 * c * Math.pow(k, 0.5);
        rd = Math.exp(-nd * b3);
        br = b * (1 - rd * bu / bc);
    }
    
     

}
