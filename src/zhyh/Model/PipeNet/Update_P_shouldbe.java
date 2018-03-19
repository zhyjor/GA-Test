/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Model.PipeNet;

import zhyh.Data.InputandClassify.StaticData2;
import zhyh.Data.MapStorage.DynamicDataMap8;
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
public class Update_P_shouldbe {

    private Map<String, Double> PipeCLMap;
    private Map<String, Double> PointOut_Pmap = new HashMap();
    private Map<String, Double> PointOut_OptiPmap = new HashMap();

    public Update_P_shouldbe(boolean TF) {
        PipeCLMap = DynamicDataMap8.Pipe_CL;
        if (TF) {//是否使用现场数据
            PipeCLMap = DynamicDataMap8.Real_Pipe_CL;
        }
        fantuiP();
        update_eachfile();
        further_update();
    }

    /**
     * 反推点的进出压力
     */
    private void fantuiP() {

        List<String> Center_station = StaticData2.getCenterStationName();
        List<String> Station = StaticData2.getStationName();
        List<String> Valve = StaticData2.getValveName();
        List<String> Well = StaticData2.getWellName();
        List<String> allStation = new ArrayList();
        allStation.addAll(Center_station);
        allStation.addAll(Station);

        Map<String, List<String>> come = StaticDataMap7.Comemap;
        Map<String, String> go = StaticDataMap7.Gomap;
        Map<String, Integer> ID = StaticDataMap7.ID;
        Map<String, Double> pipe_inP = DynamicDataMap8.pipe_InP;
        Map<String, Double> pipe_outP = DynamicDataMap8.pipe_OutP;

        List<String> nextround = allStation;
        while (!nextround.isEmpty()) {
            nextround = new ArrayList();
            nextstation:
            for (String o : allStation) {//第一轮以站开始
                double Pz = pipe_outP.get(o);
                if (ID.get(o) < 2) {//该方法在第二轮以后才会有用，对于非站的点，入口压力等于出口压力
                    Pz = PointOut_Pmap.get(o);
                }
                List<String> comelist = new ArrayList();
                try {
                    comelist = come.get(o);
                    nextround.addAll(comelist);
                } catch (Exception e) {
                    continue nextstation;
                }

                double Pq = 0;
                double Pq_opt = 0;
                String name;

                for (String m : comelist) {
                    name = m + o;
                    double q = DynamicDataMap8.RealQ.get(name);
                    double CL = PipeCLMap.get(name);
                    Pq = Pq(CL, Pz, q);
                    Pq_opt = Pq(CL, Pz, DynamicDataMap8.Opti_pipeQ.get(name));
//                    System.out.println("dian::  stationP=" + o + "/" + m + " ;Pz=" + Pz + " ;" + " ;q=" + q + " ;CL=" + CL + " ; Pq=" + Pq);
                    PointOut_Pmap.put(m, Pq);
                    PointOut_OptiPmap.put(m, Pq_opt);
                }
            }
            allStation = nextround;
        }

    }

    static private double Pq(double CL, double Pz, double Q) {
        double q = Q / 86400.0;
        double p = (Pz + 0.1) * 1000000.0;
        double x = CL * q * q + p * p;
        double pq = Math.pow(x, 0.5) / 1000000.0 - 0.1;
        return pq;
    }

    private void further_update() {
        List<Integer> pipeNo = new ArrayList();
        Map<String, Integer> ID = StaticDataMap7.ID;
        List<Double> startP_shouldbe = new ArrayList();
        List<Double> Opti_startP_shouldbe = new ArrayList();
        List<Double> Opti_EndP = new ArrayList();
        List<Double> EndP_shouldbe = new ArrayList();
        int num = StaticDataMap7.PipeStartPointList.size();
        for (int i = 0; i < num; i++) {
            String o = StaticDataMap7.PipeStartPointList.get(i);
            startP_shouldbe.add(PointOut_Pmap.get(o));
            Opti_startP_shouldbe.add(PointOut_OptiPmap.get(o));
            pipeNo.add(i + 1);
        }
        for (String o : StaticDataMap7.PipeEndPointList) {
            if (ID.get(o) < 2) {
                EndP_shouldbe.add(PointOut_Pmap.get(o));
                Opti_EndP.add(PointOut_OptiPmap.get(o));
            } else {
                EndP_shouldbe.add(DynamicDataMap8.pipe_OutP.get(o));
                Opti_EndP.add(DynamicDataMap8.pipe_OutP.get(o));
            }
        }

        db.UpdateData("Pipeline", "Start_P_Shouldbe", startP_shouldbe, "SequenceNumber", pipeNo);
        db.UpdateData("Pipeline", "Opti_StartP", Opti_startP_shouldbe, "SequenceNumber", pipeNo);
        db.UpdateData("Pipeline", "EndP_shouldbe", EndP_shouldbe, "SequenceNumber", pipeNo);
        db.UpdateData("Pipeline", "Opti_EndP", Opti_EndP, "SequenceNumber", pipeNo);
    }
    private List<Double> data;
    private DBcontroller db = new DBcontroller();

    /**
     * 根据统计数据更新其他几张表
     */
    private void update_eachfile() {

        data = new ArrayList();

        for (String o : StaticData2.getCenterStationName()) {
            data.add(PointOut_Pmap.get(o));
        }
        db.UpdateData("CenterStation", "OutP_shouldbe", data, "Name", StaticData2.getCenterStationName());

        data = new ArrayList();
        List<Double> temp = new ArrayList();
        for (String o : StaticData2.getStationName()) {
            data.add(PointOut_Pmap.get(o));
            temp.add(PointOut_OptiPmap.get(o));
        }
        db.UpdateData("Station", "OutP_shouldbe", data, "Name", StaticData2.getStationName());
        db.UpdateData("Station", "Opti_OutP", temp, "Name", StaticData2.getStationName());

        data = new ArrayList();
        temp = new ArrayList();
        for (String o : StaticData2.getValveName()) {
            data.add(PointOut_Pmap.get(o));
            temp.add(PointOut_OptiPmap.get(o));
        }
        db.UpdateData("Valve", "P_shouldbe", data, "Name", StaticData2.getValveName());
        db.UpdateData("Valve", "Opti_P", temp, "Name", StaticData2.getValveName());

        data = new ArrayList();
        temp = new ArrayList();
        for (String o : StaticData2.getWellName()) {
            data.add(PointOut_Pmap.get(o));
            temp.add(PointOut_OptiPmap.get(o));
        }
        db.UpdateData("Well", "BackP_shouldbe", data, "Name", StaticData2.getWellName());
        db.UpdateData("Well", "Opti_BackP", temp, "Name", StaticData2.getWellName());
    }

}
