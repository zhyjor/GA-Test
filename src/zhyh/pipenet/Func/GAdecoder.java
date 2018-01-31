/*
 *自行编写遗传算法遗传信息解码方法
 */
package zhyh.pipenet.Func;

import java.util.List;

/**
 * 遗传算法的解码器，主要用于遗传算法中的适应值计算 ，为GA的附属类
 * 本类是将每个区间的实际值*1000后投影至0-2^20的坐标轴上，方便GA中二进制编码计算，得到结果后在反向回转
 *
 * @author 武浩
 */
public class GAdecoder {

    static public double max[];//各个参数的最大值
    static public double min[];//各个参数的最小值
    static private int Max[];//为二进制取值方便，将上面范围转换为0-2^20范围上的整数取值，即坐标转换
    static private int Min[];
    static private int zuidaqujian = 0;//各个区间中最大宽度
    static public int weishu = 20;//二进制的位数
    static private double binaryValueRange = Math.pow(2, weishu) - 1;
    //将各个变量的取值区间化成：0-2^位数范围
    static private double quzheng = 1000;//3位小数精度，小数乘以1000后取整，
    static int num;//变量个数
    private Algo_Model_link_Opti_Model amlm;

    /**
     * 填入所有变量的最大最小范围
     */
    public GAdecoder() {
        amlm = new Algo_Model_link_Opti_Model();
        this.max = amlm.Qmax;
        this.min = amlm.Qmin;
        this.num = max.length;
        this.Max = new int[num];
        this.Min = new int[num];
        for (int i = 0; i < num; i++) {
            Max[i] = (int) (max[i] * quzheng);
            Min[i] = (int) (min[i] * quzheng);
            if ((Max[i] - Min[i]) > zuidaqujian) {
                zuidaqujian = Max[i] - Min[i];
            }
        }
    }

    /**
     * 解码，二进制码解为十进制
     */
    synchronized private int decode(int[] value) {
        int n = 0;
        for (int i = weishu - 1; i >= 0; i--) {
            n = (int) (n + value[i] * Math.pow(2, weishu - 1 - i));
        }
        return n;
    }

    /**
     * 将DNA拆分成解串，座标回转
     */
    synchronized public double[] DNAreader(List<Integer> dna) {

        double value[] = new double[num];
        int[] t = new int[weishu];
        int jishu = 0;
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < weishu; j++) {
                t[j] = dna.get(jishu);
                jishu++;
            }
            value[i] = ((int) (decode(t) / binaryValueRange * (Max[i] - Min[i]) + Min[i])) / quzheng;
        }
        return value;
    }

}
