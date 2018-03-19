/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Model.PipeNet;

import zhyh.Data.InputandClassify.NameList4;
import zhyh.Data.InputandClassify.StaticData2;
import zhyh.Data.MapStorage.DynamicDataMap8;
import static zhyh.Data.MapStorage.DynamicDataMap8.pipe_InP;
import static zhyh.Data.MapStorage.DynamicDataMap8.pipe_InT;
import static zhyh.Data.MapStorage.DynamicDataMap8.pipe_OutP;
import static zhyh.Data.MapStorage.DynamicDataMap8.pipe_OutT;
import zhyh.Data.MapStorage.StaticDataMap7;
import zhyh.Tool.Data_resource.DBcontroller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 武浩
 */
public class Update_pipe_data2 {

    private Map<String, Double> realQmap;//各口井的实际产量
    private List<String> namelist;//全部点的名称
    private Map<String, List<String>> comelist;//各个点的下属子节点，或上游节点
    private Map<String, Integer> ID;//点的属性
    private Map<String, Integer> No;//管道的编号
    private Map<String, String> Gomap;//一个点的下游点
    private DBcontroller db = new DBcontroller();//数据库操作工具
    private Update_EconomicalQ eco_q;
    private Map<String, Double> Eco_Q;
    private List<Double> pipe_eco_Q;
    private List<Double> pipe_ideal_Q;
    private List<Double> uti_rate;//管道实际输送效率
    private Map<String, Double> pipe_CL;
    private Map<String, Double> point_realQ;
    private List<Double> Centerstation_Q;
    private List<Double> Station_Q;
    private List<Double> Valve_Q;
    private List<Double> Well_Q;

    /**
     * 根据管道实际流量推算管道的压降
     */
    public void update(boolean TF) {
        Centerstation_Q = new ArrayList();
        Station_Q = new ArrayList();
        Valve_Q = new ArrayList();
        Well_Q = new ArrayList();

        pipe_CL = DynamicDataMap8.Pipe_CL;
        if (TF) {
            pipe_CL = DynamicDataMap8.Real_Pipe_CL;
        }

        realQmap = DynamicDataMap8.WellQ;
        comelist = StaticDataMap7.Allbelongmap;
        namelist = NameList4.namelistPipe;
        ID = StaticDataMap7.ID;
        No = StaticDataMap7.PipeNo;
        Gomap = StaticDataMap7.Gomap;
        eco_q = new Update_EconomicalQ();
        pipe_eco_Q = new ArrayList();
        pipe_ideal_Q = new ArrayList();
        uti_rate = new ArrayList();
        eco_q.pipe_inPmap = DynamicDataMap8.pipe_InP;
        eco_q.pipe_outPmap = DynamicDataMap8.pipe_OutP;
        eco_q.economicalQ();
        Eco_Q = eco_q.economicalQ;
        List<Double> realQ = new ArrayList();
        List<Integer> no = new ArrayList();
        String name;
        point_realQ = new HashMap();
        for (String o : namelist) {
            name = o + Gomap.get(o);
            double q = sum_Q(o);
            realQ.add(q);
            no.add(No.get(name));
        }

        pipe_PTQ();
        update_eachfile();
    }

    /**
     * 输入点名，查询该点流量，
     */
    private double sum_Q(String point) {
        List<String> temp = comelist.get(point);
        temp.add(point);
        double leijia = 0.0;
        for (String o : temp) {
            if (ID.get(o) == 0) {
                leijia = leijia + realQmap.get(o);
            }
        }
        point_realQ.put(point, leijia);
        return leijia;
    }

    private void pipe_PTQ() {
        int num = StaticDataMap7.PipeStartPointList.size();
        String qidian, zhongdian;
        List<Double> Inp = new ArrayList();
        List<Double> Int = new ArrayList();
        List<Double> Outp = new ArrayList();
        List<Double> Outt = new ArrayList();
        List<Integer> No = new ArrayList();
        List<Double> trans_rate = new ArrayList();

        for (int i = 0; i < num; i++) {
            qidian = StaticDataMap7.PipeStartPointList.get(i);
            zhongdian = StaticDataMap7.PipeEndPointList.get(i);
            double inP = pipe_InP.get(qidian);
            Inp.add(inP);
            Int.add(pipe_InT.get(qidian));
            double outP = pipe_OutP.get(zhongdian);
            Outp.add(outP);
            Outt.add(pipe_OutT.get(zhongdian));
            double eco_q = Eco_Q.get(qidian + zhongdian);
            double theo_q = idealQ(inP, outP, pipe_CL.get(qidian + zhongdian));
            pipe_eco_Q.add(eco_q);
            pipe_ideal_Q.add(theo_q);
            uti_rate.add(DynamicDataMap8.RealQ.get(qidian + zhongdian) / eco_q);
            trans_rate.add(DynamicDataMap8.RealQ.get(qidian + zhongdian) / theo_q);
            No.add(i + 1);
        }

        db.UpdateData("Pipeline", "Ecnomical_Q", pipe_eco_Q, "SequenceNumber", No);
        db.UpdateData("Pipeline", "TheoreticalQ", pipe_ideal_Q, "SequenceNumber", No);
        db.UpdateData("Pipeline", "UtilizationEfficiency", uti_rate, "SequenceNumber", No);
        db.UpdateData("Pipeline", "TransportEfficiency", trans_rate, "SequenceNumber", No);
    }

    private double idealQ(double qidainP, double zhongdianP, double CL) {
        double q;
        double qd;
        double zd;
        double startP = qidainP + 0.1;
        double endP = zhongdianP + 0.1;
        double temp = (startP * startP - endP * endP) / CL;
        if (temp > 0) {
            q = Math.pow(temp, 0.5) * 1000000.0 * 86400.0;
        } else {
            q = 0;
        }
        return q;
    }

    /**
     * 根据统计数据更新其他几张表
     */
    private void update_eachfile() {

        for (String o : StaticData2.getCenterStationName()) {
            Centerstation_Q.add(point_realQ.get(o));
        }
        db.UpdateData("CenterStation", "Q_shouldbe", Centerstation_Q, "Name", StaticData2.getCenterStationName());

        for (String o : StaticData2.getStationName()) {
            Station_Q.add(point_realQ.get(o));
        }
        db.UpdateData("Station", "Q_shouldbe", Station_Q, "Name", StaticData2.getStationName());

        for (String o : StaticData2.getValveName()) {
            Valve_Q.add(point_realQ.get(o));
        }
        db.UpdateData("Valve", "Q_shouldbe", Valve_Q, "Name", StaticData2.getValveName());

    }
}
