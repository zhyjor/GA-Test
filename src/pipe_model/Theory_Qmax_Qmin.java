/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipe_model;

import Model.PipeNet.*;
import Data.InputandClassify.NameList4;
import Data.InputandClassify.StaticData2;
import Data.MapStorage.DynamicDataMap8;
import static Data.MapStorage.DynamicDataMap8.pipe_InP;
import static Data.MapStorage.DynamicDataMap8.pipe_InT;
import static Data.MapStorage.DynamicDataMap8.pipe_OutP;
import static Data.MapStorage.DynamicDataMap8.pipe_OutT;
import Data.MapStorage.StaticDataMap7;
import Tool.Data_resource.DBcontroller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Theory_Qmax_Qmin
 */
public class Theory_Qmax_Qmin {

    private Map<String, Double> realQmap;//各口井的实际产量
    private List<String> namelist;//全部点的名称
    private Map<String, List<String>> comelist;//各个点的下属子节点，或上游节点
    private Map<String, Integer> ID;//点的属性
    private Map<String, Integer> No;//管道的编号
    private Map<String, String> Gomap;//一个点的下游点
    private DBcontroller db = new DBcontroller();//数据库操作工具
   
    private List<Double> pipe_ideal_Q;
    
    private Map<String, Double> pipe_CL;

    public void update(boolean TF) {
       

        pipe_CL = DynamicDataMap8.Pipe_CL;
      
        realQmap = DynamicDataMap8.WellQ;
        comelist = StaticDataMap7.Allbelongmap;
        namelist = NameList4.namelistPipe;
        ID = StaticDataMap7.ID;
        No = StaticDataMap7.PipeNo;
        Gomap = StaticDataMap7.Gomap;
        
        pipe_ideal_Q = new ArrayList();
        pipe_PTQ();
       
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
            
            double theo_q = idealQ(inP, outP, pipe_CL.get(qidian + zhongdian));
           
            pipe_ideal_Q.add(theo_q);
           
            No.add(i + 1);
        }

        
        db.UpdateData("Pipeline", "TheoreticalQ", pipe_ideal_Q, "SequenceNumber", No);
      
       
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
}
