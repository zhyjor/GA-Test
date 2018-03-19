/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Estimater;

import zhyh.Tool.Data_resource.DBcontroller;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 根据井底流压评价抽水机的工作状态
 */
public class Estimater_Equipment {

    private DBcontroller db = new DBcontroller();
    private List<String> wellname = new ArrayList();
    private List<Double> Pwf_best = new ArrayList();
    private List<Double> Pwf_max = new ArrayList();
    private List<Double> Pwf_min = new ArrayList();
    private List<Double> Pwf_real = new ArrayList();
    private List<Double> estimate_result = new ArrayList();

    public Estimater_Equipment() {
        ResultSet result = db.getFile("Well");
        wellname = db.ReadString(result, "Name");
        Pwf_best = db.ReadDouble(result, "Best_Pwf");
        Pwf_max = db.ReadDouble(result, "Pwf_max");
        Pwf_min = db.ReadDouble(result, "Pwf_min");
        Pwf_real = db.ReadDouble(result, "FBHP");
    }

    public void estimate() {
        estimate_result = new ArrayList();
        int num = wellname.size();
        double pwf_best;
        double pwf_real;
        double pwf_max;
        double pwf_min;
        double rate = 0;
        for (int i = 0; i < num; i++) {
            pwf_best = Pwf_best.get(i);
            pwf_max = Pwf_max.get(i);
            pwf_min = Pwf_min.get(i);
            pwf_real = Pwf_real.get(i);
            if (pwf_real <= pwf_min || pwf_real >= pwf_max) {
                rate = 0;
            } else if (pwf_real <= pwf_best) {
                rate = (pwf_real - pwf_min) / (pwf_best - pwf_min);
            } else if (pwf_real > pwf_best) {
                rate = (pwf_max - pwf_real) / (pwf_max - pwf_best);
            }
            estimate_result.add(rate);
        }
        db.UpdateData("Well", "EquipmentRunningEfficiency", estimate_result, "Name", wellname);
    }

}
