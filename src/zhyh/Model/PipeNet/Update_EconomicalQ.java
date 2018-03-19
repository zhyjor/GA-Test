/*
 *用于Update_pipe_data类中，计算管道经济流量
 */
package zhyh.Model.PipeNet;

import zhyh.Data.MapStorage.DynamicDataMap8;
import zhyh.Data.MapStorage.StaticDataMap7;
import zhyh.Model.Pipe.GuanPpj;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于Update_pipe_data类
 *
 * @author 武浩
 */
public class Update_EconomicalQ {

    public Map<String, Double> economicalQ = new HashMap();//理论流量，或者经济流量
    private List<String> pipe_start_point = new ArrayList();//管道起点
    private List<String> pipe_end_point = new ArrayList();//管道终点
    private Map<String, Double> D = new HashMap();//管径，mm
    private Map<String, Double> real_Ppj = new HashMap();
    public Map<String, Double> pipe_inPmap;
    public Map<String, Double> pipe_outPmap;

    public Update_EconomicalQ() {
        Ppj();
    }

    /**
     * 计算理论流量
     */
    public void economicalQ() {
        pipe_start_point = StaticDataMap7.PipeStartPointList;
        pipe_end_point = StaticDataMap7.PipeEndPointList;
        D = StaticDataMap7.PipeDmap;
        String name;
        double v;
        double A;
        double d;
        double q;
        int num = pipe_start_point.size();
        for (int i = 0; i < num; i++) {
            name = pipe_start_point.get(i) + pipe_end_point.get(i);
            v = StaticDataMap7.Economical_Speed.get(name);//经济流速,m/s
            d = D.get(name) / 1000.0;//管径,m
            A = 3.14 * d * d / 4.0;//管道截面积，m2
            double Z = real_Ppj.get(name) / 0.1;
            q = A * v * 86400.0 * Z;//流量,m3/d
            economicalQ.put(name, q);
        }
    }

    private void Ppj() {
        List<String> pipe_Start = StaticDataMap7.PipeStartPointList;
        List<String> pipe_End = StaticDataMap7.PipeEndPointList;
        GuanPpj gp = new GuanPpj();
        int num = pipe_End.size();
        double in_p;
        double out_P;
        double Ppj;
        String name;
        for (int i = 0; i < num; i++) {
            name = pipe_Start.get(i) + pipe_End.get(i);
            in_p = DynamicDataMap8.pipe_InP.get(pipe_Start.get(i)) + 0.1;
            out_P = DynamicDataMap8.pipe_OutP.get(pipe_End.get(i)) + 0.1;
            Ppj = gp.Ppj(in_p, out_P);
            real_Ppj.put(name, Ppj);
        }

    }
}
