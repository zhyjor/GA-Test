/*
 * 单独功能，根据井的产量累加，更新管道实际流量
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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据实际流量与井口压力计算管网各点的流量与压力
 *
 * @author 武浩
 */
public class Update_pipe_data1 {

    private Map<String, List<String>> allbelong;//各个点的下属子节点，或上游节点
    private Map<String, Integer> ID;//点的属性
    private Map<String, Integer> No;//管道的编号
    private Map<String, String> Gomap;//一个点的下游点
    private DBcontroller db = new DBcontroller();//数据库操作工具
    private Map<String, Double> point_realQ;
    private List<Double> Centerstation_Q;
    private List<Double> Station_Q;
    private List<Double> Valve_Q;
    private List<Double> Well_Q;
    /**
     * 下面的是计算井筒压力用的
     */
    private List<Double> well_bore_P;
    private List<Double> well_lipuid_level;
    private List<Double> formation_depth;
    private List<String> namelist;
    private List<Double> formation_thickness;
    private ResultSet rs;

    /**
     * 根据管道实际流量推算管道的压降
     */
    public void update() {
        well_bore_P = new ArrayList();
        rs = db.getFile("Well");
        namelist = db.ReadString(rs, "Name");
        formation_depth = db.ReadDouble(rs, "FormationDepth");
        well_lipuid_level = db.ReadDouble(rs, "Working_Fluid_Level");
        formation_thickness = db.ReadDouble(rs, "PFThickness");
        int num = namelist.size();
        double p;
        double h;
        for (int i = 0; i < num; i++) {
            h = formation_depth.get(i) - well_lipuid_level.get(i);
            if (h < 0) {
                h = 0;
            }
            p = (h + formation_thickness.get(i) / 2.0) * 0.01;//井筒液柱压力，MPa
            well_bore_P.add(p);
        }
        db.UpdateData("Well", "Well_bore_P", well_bore_P, "Name", namelist);

        Centerstation_Q = new ArrayList();
        Station_Q = new ArrayList();
        Valve_Q = new ArrayList();
        Well_Q = new ArrayList();

        allbelong = StaticDataMap7.Allbelongmap;
        ID = StaticDataMap7.ID;
        No = StaticDataMap7.PipeNo;
        Gomap = StaticDataMap7.Gomap;
        List<Double> realQ = new ArrayList();
        List<Integer> no = new ArrayList();
        String name;
        point_realQ = new HashMap();
        for (String o : NameList4.namelistPipe) {
            name = o + Gomap.get(o);
            double q = sum_Q(o);
            realQ.add(q);
            no.add(No.get(name));
        }
        db.UpdateData("Pipeline", "Q_shouldbe", realQ, "SequenceNumber", no);
        pipe_PTQ();
        update_eachfile();
    }

    /**
     * 输入点名，查询该点流量，
     */
    private double sum_Q(String point) {
        List<String> temp = new ArrayList();
        temp.addAll(allbelong.get(point));
        temp.add(point);
        double leijia = 0.0;
        for (String o : temp) {
            if (ID.get(o) == 0) {
                leijia = leijia + DynamicDataMap8.WellQ.get(o);
            }
        }
        point_realQ.put(point, leijia);
        return leijia;
    }

    /**
     * 更新管道起终点的实际参数
     */
    private void pipe_PTQ() {
        int num = StaticDataMap7.PipeStartPointList.size();
        String qidian, zhongdian;
        List<Double> Inp = new ArrayList();
        List<Double> Int = new ArrayList();
        List<Double> Outp = new ArrayList();
        List<Double> Outt = new ArrayList();
        List<Integer> No = new ArrayList();

        for (int i = 0; i < num; i++) {
            qidian = StaticDataMap7.PipeStartPointList.get(i);
            zhongdian = StaticDataMap7.PipeEndPointList.get(i);
            double inP = pipe_InP.get(qidian);
            Inp.add(inP);
            Int.add(pipe_InT.get(qidian));
            double outP = pipe_OutP.get(zhongdian);
            Outp.add(outP);
            Outt.add(pipe_OutT.get(zhongdian));

            No.add(i + 1);
        }

        db.UpdateData("Pipeline", "StartP", Inp, "SequenceNumber", No);
        db.UpdateData("Pipeline", "StartT", Int, "SequenceNumber", No);
        db.UpdateData("Pipeline", "EndP", Outp, "SequenceNumber", No);
        db.UpdateData("Pipeline", "EndT", Outt, "SequenceNumber", No);

//        System.out.println("/////////////////////////////////////////////////pipe_ideal_Q==" + pipe_ideal_Q);
        System.out.println("Update_pipe_data:管道数据更新完毕！");
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
