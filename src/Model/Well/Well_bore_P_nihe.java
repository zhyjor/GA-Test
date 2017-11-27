/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Well;

import Data.InputandClassify.AllData1;
import Data.InputandClassify.StaticData2;
import Tool.Data_resource.DBcontroller;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wuhao
 */
public class Well_bore_P_nihe {

    private ResultSet resultset;
    private List<Double> formationdepth;
    private List<String> welllist;
    private WellboreP wp;
    public Map<String, Double> minP;//计算0.2MPa时的压力，输入井的名字查询
    public Map<String, Double> maxP;//计算10MP时的压力，输入井的名字查询
    private DBcontroller db = new DBcontroller();
    private List<Double> D;
    private List<Double> d;
    private List<Double> Tavg;

    public Well_bore_P_nihe() {
        resultset = AllData1.WellSet;
        formationdepth = db.ReadDouble(resultset, "FormationDepth");
        D = db.ReadDouble(resultset, "CasingD");
        d = db.ReadDouble(resultset, "TubingD");
        welllist = StaticData2.getWellName();
        wp = new WellboreP();
        Tavg = db.ReadDouble(resultset, "T");
        minP = new HashMap();
        maxP = new HashMap();
        well_hole_P();
    }

    /**
     * 假设井筒只有气体，且气柱压力随着井口压力的变化线性变化
     */
    private void well_hole_P() {
        double formationdeep;
        int num = welllist.size();
        double D;
        double d;
        double T;
        double p;
        for (int i = 0; i < num; i++) {
            formationdeep = formationdepth.get(i);
            D = this.D.get(i) / 1000.0;
            d = this.d.get(i) / 1000.0;
            T = Tavg.get(i) + 273.15;
            p = wp.P(formationdeep, D, d, 0.2, 0.5, T, formationdeep);//井口0.2MPa,产量5000m3/d
            minP.put(welllist.get(i), p - 0.2);
            p = wp.P(formationdeep, D, d, 10.0, 0.5, T, formationdeep);//井口10MPa,产量5000m3/d
            maxP.put(welllist.get(i), p - 10.0);
        }
    }

}
