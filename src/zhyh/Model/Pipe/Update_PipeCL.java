/*
 * 管道常数C
 */
package zhyh.Model.Pipe;

import zhyh.Data.MapStorage.DynamicDataMap8;
import zhyh.Data.MapStorage.StaticDataMap7;
import java.util.ArrayList;
import java.util.List;

/**
 * 单独的功能，用于Mozuxishu类中，直接更新数据库中的数据
 *
 * @author 武浩
 */
public class Update_PipeCL {

    /*
     * Pq^2-Pz^2=CLQ^2,本方法用于计算CL,需要输入管道起点，用于方法pipeC()中，本类中参数单位为国际单位
     */
    private static GuanQ guanq = new GuanQ();
    private static GuanT guant = new GuanT();
    private static double delta = 0.59;//天然气相对密度.
    private static double Co = 0.03848;//输气管道课本P112
    private static double temp = delta / Co / Co;

    /**
     * 计算管道Po^2-Pz^2=CLQ^2 中CL
     */
    protected double Ideal_PipeCL(String qidian, String zhongdian, double mozu) {
        String pipe = qidian + zhongdian;//管道名称
        double qidianT = DynamicDataMap8.pipe_InT.get(qidian) + 273.15;//起点温度
        double zhongdianT = DynamicDataMap8.pipe_OutT.get(zhongdian) + 273.15;//终点温度
        double D = (StaticDataMap7.PipeDmap.get(pipe)) / 1000.0;//管径
        double L = StaticDataMap7.PipeLengthmap.get(pipe);//管长
        double To = DynamicDataMap8.To.get(pipe) + 273.15;//环境温度
        double a = 0;
        double Tpj = 0;

        a = guant.a(qidianT, zhongdianT, To, L);
        if (a != 0) {//防止数据不合适报错
            Tpj = To + (qidianT - To) * (1 - Math.exp(-a * L)) / a / L;//平均温度
        } else {
            Tpj = (qidianT + zhongdianT) / 2;
        }

        double C = temp * L / Math.pow(D, 5) * Tpj * mozu;
        return C;
    }

    /**
     * 实际生产参数已知且可靠的时候
     */
    protected double Real_PipeCL(String qidian, String zhongdian) {
        double Pq = (DynamicDataMap8.pipe_InP.get(qidian) + 0.1) * 1000000.0;
        double Pz = (DynamicDataMap8.pipe_OutP.get(zhongdian) + 0.1) * 1000000.0;
        double q = sum_Q(qidian) / 86400.0;
        double x = (Pq * Pq - Pz * Pz) / q / q;
        if (x <= 0) {
            System.out.println("管道"+qidian+"=>"+zhongdian+"参数：Pq="+Pq+" ;Pz="+Pz+" ;q="+q+" ;x="+x);
        }
//        System.out.println("//////////////PipeCL::管道：" + qidian + "/" + zhongdian + " ;Pq=" + Pq + " ;Pz=" + Pz + " ;Q=" + q * 86400.0 + " x=" + x);
        return x;
    }

    /**
     * 输入点名，查询该点流量，
     */
    private double sum_Q(String point) {
        List<String> temp = new ArrayList();
        temp.addAll(StaticDataMap7.Allbelongmap.get(point));
        temp.add(point);
        double leijia = 0.0;
        for (String o : temp) {
            if (StaticDataMap7.ID.get(o) == 0) {
                leijia = leijia + DynamicDataMap8.WellQ.get(o);
            }
        }
        return leijia;
    }
}
