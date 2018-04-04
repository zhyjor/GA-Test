/*
 * 本类主要是提供井产量上下限
 */
package pipe_model;

import Data.InputandClassify.StaticData2;
import Data.MapStorage.StaticDataMap7;
import Model.Pipe.GuanPpj;
import Tool.Data_resource.DBcontroller;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据井的生产压差限制推算其产量的限制，用的是二项式产能方程
 *
 * @author 武浩
 */
public class Start_Qmax_Qmin {

   
    static private double Pmax;
    static private double Pmin;
   

    /**
     * 下面的通过运行本类方法获得
     */
    public Map<String, Double> Qmaxmap;//各个井的产量上限
    public Map<String, Double> Qminmap;//各个井的产量下限
    public Map<String, Double> Pmaxmap;//各个井的产量上限
    public Map<String, Double> Pminmap;//各个井的产量下限
    private Map<String, Integer> ID;//查询点的属性，0为井
    private Start_Pmax_Pmin cpp;
    
    private DBcontroller db = new DBcontroller();//数据库操作工具

    private ResultSet rs;
    private List<String> pipestart;
    private List<Double> pipestartP;

    private Map<String, Double> pointQTheoMax;
    private Map<String, Double> pointQTheoMin;

    private List<Double> pipeendP;
    private List<Double> D;
    private List<Double> thickness;
    private List<Double> ecoSpeed;
    static public List<Double> qmaxlist = new ArrayList();
    static public List<Double> qminlist = new ArrayList();
    static public List<String> namelist = new ArrayList();
    private List<Integer> Num;


    

    public Start_Qmax_Qmin() {
        cpp = new Start_Pmax_Pmin();
        ID = StaticDataMap7.ID;
        qmaxlist = new ArrayList();
        qminlist = new ArrayList();
        namelist = StaticData2.getWellName();
        Num = new ArrayList();

    }

    /**
     * 便利全部点，写入Map，用于Link_Coalseam_Model类中
     */
    protected void input_wellList() {
        Qmaxmap = new HashMap();
        Qminmap = new HashMap();
        Pmaxmap = new HashMap();
        Pminmap = new HashMap();
        reReadDB();
    }

    /**
     * 输入井名称，算出该井的产量上下限
     */
    private void input_wellname(String name) {
        cpp.input_wellname(name);
        Start_Qmax_Qmin.Pmax = cpp.Pmax;
        Start_Qmax_Qmin.Pmin = cpp.Pmin;
        Pmaxmap.put(name, Pmax);
        Pminmap.put(name, Pmin);
        }

    private void reReadDB() {
        rs = db.getFile("Pipeline");
        pipestart = db.ReadString(rs, "StartPointName");
        pipestartP = db.ReadDouble(rs, "StartP");
        pipeendP = db.ReadDouble(rs, "EndP");
        D = db.ReadDouble(rs, "D");
        thickness = db.ReadDouble(rs, "Thickness");
        ecoSpeed = db.ReadDouble(rs, "Economical_Speed");
       
        pointQTheoMax = pipe_OptiP_theoricalQ("max");
        pointQTheoMin = pipe_OptiP_theoricalQ("min");
        
        
         int num = namelist.size();
        for (int i = 0; i < num; i++) {
            Num.add(i + 1);
            qmaxlist.add(pointQTheoMax.get(namelist.get(i)));
            qminlist.add(pointQTheoMin.get(namelist.get(i)));
        }
        System.out.println("Q_max==" + qmaxlist);
        db.UpdateData("Well", "Q_max", qmaxlist, "SequenceNumber", Num);
        db.UpdateData("Well", "Q_min", qminlist, "SequenceNumber", Num);
        System.out.println("Start_Qmax_Qmin： well Q范围 更新完毕!!");
        
    }
    
    private Map pipe_OptiP_theoricalQ(String type) {
        GuanPpj gp = new GuanPpj();
        int num = pipestart.size();
        double Ppj;
        double d;
        double startP;
        double endP;
        double q;
        double v;
        double Z;
        double pointStartP;
        double pointEndP = 2;
        Map<String, Double> pointQTheoMap = new HashMap<String, Double>();

        
        if(type =="max"){
            pointStartP = 4;
        }else{
            pointStartP = 2;
        }

        for (int i = 0; i < num; i++) {
            Ppj = gp.Ppj(pointStartP + 0.1, pointEndP + 0.1);
            Z = Ppj / 0.1;
            startP = pipestartP.get(i);
            endP = pipeendP.get(i);
            v = ecoSpeed.get(i);
            d = (D.get(i) - 2 * thickness.get(i)) / 1000.0;
            q = 3.14 * d * d / 4.0 * v * 86400.0 * Z;
            
            pointQTheoMap.put(pipestart.get(i), q);
        }
        System.out.println(pointQTheoMap);
        return pointQTheoMap;
    }
    
   

}
