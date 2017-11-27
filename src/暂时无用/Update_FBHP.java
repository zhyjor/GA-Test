/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Well;

import Data.InputandClassify.DynamicData3;
import Data.InputandClassify.Starter_first;
import Data.InputandClassify.StaticData2;
import Tool.Data_resource.DBcontroller;
import java.util.List;

/**
 * 根据现在的生产数据估算井底流压
 *
 * @author 武浩
 */
public class Update_FBHP {

    private ChannengEquation ce = new ChannengEquation();
    private List<String> namelist;
    private List<Double> A;
    private List<Double> B;
    private List<Double> Pr;
    private List<Double> Qg;
    private DBcontroller db;
    private Starter_first sf;

    public void update() {
        sf = new Starter_first();
        db = new DBcontroller();
        sf.starter();
        namelist = StaticData2.getWellName();
        A = StaticData2.getEquaA();
        B = StaticData2.getEquaB();
        Pr = DynamicData3.WellPr;
        Qg = DynamicData3.WellQ;
        int num = namelist.size();
        double pwf;
        for (int i = 0; i < num; i++) {
            pwf = ce.Pwf(A.get(i), B.get(i), Pr.get(i), Qg.get(i));
            db.UpdateData("Well", "FBHP", pwf, "SequenceNumber", i + 1);
        }
        System.out.println("井底流压计算完毕！");
    }

    public static void main(String[] args) {

        double a = 0.000540;
        double b = -0.00000001590;
        double pr = 2.0433;
        double pwf = 0.581;
        double c = pr * pr - pwf * pwf;
        System.out.println("4*b*c=" + 4*b*c);
        System.out.println("a*a=" + a * a);
        double q = 2200;
        double p = pr * pr - a * q - b * q * q;
        System.out.println(p);
    }
}
