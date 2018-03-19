/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Model.PipeNet;

import zhyh.Model.Pipe.GuanPpj;
import zhyh.Tool.Data_resource.DBcontroller;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *目测是计算理论Q和实际的优化后的Q的比值，rate,计算优化率
 * @author wuhao
 */
public class Update_pipe_data3 {

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
    private List<Double> trans_rate;//管道实际输送效率
    private Map<String, Double> pipe_CL;
    private Map<String, Double> point_realQ;
    private List<Double> Centerstation_Q;
    private List<Double> Station_Q;
    private List<Double> Valve_Q;
    private List<Double> Well_Q;

    private ResultSet rs;
    private List<String> pipestart;
    private List<Double> pipestartP;
    private List<Double> pipeendP;
    private List<Double> optiQ;
    private List<Double> D;
    private List<Double> thickness;
    private List<Double> ecoSpeed;
    private List<Double> rate;

    public Update_pipe_data3() {
        reReadDB();
    }

    private void reReadDB() {
        rs = db.getFile("Pipeline");
        pipestart = db.ReadString(rs, "StartPointName");
        pipestartP = db.ReadDouble(rs, "StartP");
        pipeendP = db.ReadDouble(rs, "EndP");
        optiQ = db.ReadDouble(rs, "Opti_Q");
        D = db.ReadDouble(rs, "D");
        thickness = db.ReadDouble(rs, "Thickness");
        ecoSpeed = db.ReadDouble(rs, "Economical_Speed");
        rate = new ArrayList();
        pipe_OptiP_theoricalQ();
    }

    private void pipe_OptiP_theoricalQ() {
        GuanPpj gp = new GuanPpj();
        int num = pipestart.size();
        double Ppj;
        double d;
        double startP;
        double endP;
        double q;
        double v;
        double Z;

        for (int i = 0; i < num; i++) {
            Ppj = gp.Ppj(pipestartP.get(i) + 0.1, pipeendP.get(i) + 0.1);
            Z = Ppj / 0.1;
            startP = pipestartP.get(i);
            endP = pipeendP.get(i);
            v = ecoSpeed.get(i);
            d = (D.get(i) - 2 * thickness.get(i)) / 1000.0;
            q = 3.14 * d * d / 4.0 * v * 86400.0 * Z;
            rate.add(optiQ.get(i) / q);
        }

        db.UpdateData("Pipeline", "Opti_Efficiency", rate, "StartPointName", pipestart);
    }

}
