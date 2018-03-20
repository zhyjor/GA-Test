/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.CoalSeam.preparedata;

import Data.InputandClassify.StaticData2;
import Data.MapStorage.DynamicDataMap8;
import Data.MapStorage.StaticDataMap7;
import Tool.Data_resource.DBcontroller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 更新数据库Pr_shouldbe列
 *
 * @author
 */
public class Update_Pr_shouldbe {

    private Map<String, Double> Well_RealQ;
    private Map<String, Double> A;
    private Map<String, Double> B;
    private Map<String, Double> Pwf;
    private List<String> welllist;
    private List<Double> Pr = new ArrayList();
    private DBcontroller db = new DBcontroller();
    private List<Double> work_liquid_level_P;//井筒液柱压力；因为气量较小，直接用水柱压力代替

    public void Pr_shouldbe() {
        Well_RealQ = DynamicDataMap8.WellQ;
        A = StaticDataMap7.EquaA;
        B = StaticDataMap7.EquaB;
        Pwf = DynamicDataMap8.Well_Pwf;
        welllist = StaticData2.getWellName();
        double a, b, q, pwf, pr = 0;
        for (String o : welllist) {
            a = A.get(o);
            b = B.get(o);
            q = Well_RealQ.get(o);
            pwf = Pwf.get(o);
            pr = Pr(a, b, q, pwf);
//            System.out.println("tt+" + o + " ;a=" + a + " ;b=" + b + " ;q=" + q + " ;Pwf=" + pwf + " pr=" + pr);
            Pr.add(pr);
        }
        db.UpdateData("Well", "Pr_shouldbe", Pr, "Name", welllist);
    }

    private double Pr(double a, double b, double q, double Pwf) {
        double pr = a * q + b * q * q + Pwf * Pwf;
        if (pr < 0) {
            pr = 0;
        }
        return Math.pow(pr, 0.5);
    }

    public static void main(String[] args) {
        double a, b, c;
        a = 0.0018;
        b = -1.17 * Math.pow(10, -8);
        c = 9.86;
        double temp = a * a + 4 * b * c;
        double x = (-a+ Math.pow(temp, 0.5)) / 2.0 / b;
        System.out.println(x);

    }
}
