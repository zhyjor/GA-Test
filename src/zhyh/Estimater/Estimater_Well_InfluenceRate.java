/*
 * 评价单井对其他井的影响
 */
package zhyh.Estimater;

import zhyh.Opti.Functions.OptiAll;
import zhyh.Opti.Functions.Opti_Result_Storager;
import zhyh.Tool.Data_resource.DBcontroller;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 武浩
 */
public class Estimater_Well_InfluenceRate {

    private OptiAll opti;
    private Opti_Result_Storager ors;
    private List<Double> Q_after_estimate;//进行单独评价时的流量结果
    public List<String> namelist_inOpti;//优化中的顺序的名称列表
    private List<Double> Q_before_estimate;//数据库中的优化流量
    private DBcontroller db = new DBcontroller();
    private Map<String, Double> well_initialQ;//评价前的产量
    private Map<String, Double> well_estQ;//评价中的产量
    private List<String> allwell;//数据库中的顺序名称列表
    public List<Double> result_est;//评价结果，比
    public List<Double> deltaQ;//评价结果，差
    public Map<String, String> well_region_map;
    private List<String> region;
    private ResultSet rs;

    private void esctimater(List<String> welllist_esc) throws InterruptedException {
        rs = db.getFile("Well");
        region = new ArrayList();
        well_region_map = new HashMap();
        allwell = new ArrayList();
        deltaQ = new ArrayList();
        well_initialQ = new HashMap();
        well_estQ = new HashMap();
        result_est = new ArrayList();
        opti = new OptiAll();
        ors = new Opti_Result_Storager();
        OptiAll.well_for_estimate = welllist_esc;
        OptiAll.output = false;//关闭结果外输
        opti.optstart();
        Q_after_estimate = Opti_Result_Storager.allresult_Q;
        Q_before_estimate = db.ReadDouble(rs, "Best_Q");
        allwell = db.ReadString(rs, "Name");
        region = db.ReadString(rs, "Region");
        namelist_inOpti = Opti_Result_Storager.allwelllist;
        int num = namelist_inOpti.size();
        String name;
        for (int i = 0; i < num; i++) {
            name = allwell.get(i);
            well_initialQ.put(name, Q_before_estimate.get(i));
            well_region_map.put(name, region.get(i));
        }
        for (int i = 0; i < num; i++) {
            name = namelist_inOpti.get(i);
            double rate = well_initialQ.get(name) / Q_after_estimate.get(i) * 100.0;
            deltaQ.add(0.0);
            if (rate > 95) {//混合遗传算法误差消除^-^
                rate = 100.0;
                deltaQ.add(well_initialQ.get(name) - Q_after_estimate.get(i));
            }
            result_est.add(rate);
        }
    }

    public void estimateWell(List<String> well) throws InterruptedException {
        esctimater(well);
    }

}
