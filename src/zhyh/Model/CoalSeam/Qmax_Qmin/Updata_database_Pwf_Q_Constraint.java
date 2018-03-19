/*
 * 本包是独立程序，用于更新各个井的压力产量的上下限，需要运行完Starter_third.starter();后启动
 */
package zhyh.Model.CoalSeam.Qmax_Qmin;

import zhyh.Data.InputandClassify.StaticData2;
import zhyh.Tool.Data_resource.DBcontroller;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 武浩
 */
public class Updata_database_Pwf_Q_Constraint {

    private DBcontroller db = new DBcontroller();
    static public List<String> namelist = new ArrayList();
    static public List<Double> qmaxlist = new ArrayList();
    static public List<Double> qminlist = new ArrayList();
    static public List<Double> pmaxlist = new ArrayList();
    static public List<Double> pminlist = new ArrayList();
    private List<Integer> Num;
    private Link_Coalseam_Model lcm;

    public Updata_database_Pwf_Q_Constraint() {
        lcm = new Link_Coalseam_Model();
        namelist = StaticData2.getWellName();
        Num = new ArrayList();
        qmaxlist = new ArrayList();
        qminlist = new ArrayList();
        pmaxlist = new ArrayList();
        pminlist = new ArrayList();
    }

    public void update() {//更井产量与井底流压的上下限

        lcm.init();
        int num = namelist.size();
        for (int i = 0; i < num; i++) {
            Num.add(i + 1);
            qmaxlist.add(lcm.Qmax.get(namelist.get(i)));
            qminlist.add(lcm.Qmin.get(namelist.get(i)));
            pmaxlist.add(lcm.Pwf_max.get(namelist.get(i)));
            pminlist.add(lcm.Pwf_min.get(namelist.get(i)));
        }
        System.out.println("Q_max==" + qmaxlist);
        db.UpdateData("Well", "Q_max", qmaxlist, "SequenceNumber", Num);
        db.UpdateData("Well", "Q_min", qminlist, "SequenceNumber", Num);
        db.UpdateData("Well", "Pwf_max", pmaxlist, "SequenceNumber", Num);
        db.UpdateData("Well", "Pwf_min", pminlist, "SequenceNumber", Num);
        System.out.println("Updata_database_Pwf_Q_Constraint：P and Q 约束更新完毕!!");
    }

}
