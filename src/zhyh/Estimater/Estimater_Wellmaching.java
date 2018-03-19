/*
 * 优化结束后进行的评价，以及出图
 */
package zhyh.Estimater;

import zhyh.Data.MapStorage.DynamicDataMap8;
import zhyh.Data.MapStorage.Starter_third;
import zhyh.Data.MapStorage.StaticDataMap7;
import zhyh.Tool.Data_resource.DBcontroller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 武浩
 */
public class Estimater_Wellmaching {

    private Map<String, Double> pipe_CL;//管道系数
    private Map<String, List<String>> path_to_station;//井到集气站的路径
    private Map<String, String> gomap;//下游点的查询
    private Map<String, Double> well_Qmax;//井的最大产量
    private Map<String, Double> well_Pwfmin;//井底最小压力
    private List<String> welllist_in_childtree;//子树中全部井
    private List<String> root_need_opti;//所有需要优化的树结构
    private Map<String, Double> pipe_out_P;//管道出口压力查询
    private DBcontroller db;//数据库操作类
    private List<Double> individual_well_prodctionQ;//井底压力最低，管网背压最低时，井单独生产时能达到的最大产量
    private Map<String, Double> deltaP_of_Wellbore;//井底到井口的压力损失
    private List<String> all_well_list;//全部井的列表
    private List<Double> well_pipenet_match;//井与管的匹配性
    private List<Double> well_match;//井与整个系统匹配性
    private Map<String, Double> equA;//产能方程系数
    private Map<String, Double> equB;//产能方程系数
    private Map<String, Double> Pr;//地层压力
    private Map<String, Double> Point_Pmap;
    private Map<String, Double> Point_OptiPmap;

    public Estimater_Wellmaching() {
        db = new DBcontroller();
        pipe_CL = DynamicDataMap8.Pipe_CL;
        path_to_station = StaticDataMap7.gotorootPath;
        gomap = StaticDataMap7.Gomap;
        well_Qmax = StaticDataMap7.Q_max;
        well_Pwfmin = StaticDataMap7.Pwf_min;
        root_need_opti = StaticDataMap7.rootofNeedOptiTree;
        pipe_out_P = DynamicDataMap8.pipe_OutP;
        deltaP_of_Wellbore = new HashMap();
        well_pipenet_match = new ArrayList();
        well_match = new ArrayList();
        equA = StaticDataMap7.EquaA;
        equB = StaticDataMap7.EquaB;
        Pr = StaticDataMap7.Pr;
        all_well_list = new ArrayList();
        individual_well_prodctionQ = new ArrayList();

    }

    /**
     * 评价方法
     */
    public void est() {
        for (String o : root_need_opti) {
            String station = gomap.get(o);//井流最终进站
            double inP = pipe_out_P.get(station);//进站压力，MPa
            welllist_in_childtree = StaticDataMap7.well_in_childtree.get(o);//井列表
            List<String> path = new ArrayList();
            for (String p : welllist_in_childtree) {//遍历各个井
                all_well_list.add(p);
                path = path_to_station.get(p);//井流进站路径
                double sum_CL = 0;
                int num = path.size();
                double a = equA.get(p);
                double b = equB.get(p);
                double pr = Pr.get(p);
                double indiv_Q;
                double Qmax = well_Qmax.get(p);
                String point;
                String pipe;
                for (int i = 0; i < num - 1; i++) {
                    point = path.get(i);
                    pipe = point + gomap.get(point);
                    sum_CL = sum_CL + pipe_CL.get(pipe) / 746496.0 / Math.pow(10.0, 16);
                    System.out.print(pipe_CL.get(pipe) / Math.pow(10.0, 12) + ">");
                }
                indiv_Q = q(a, b, sum_CL, pr, inP);
                individual_well_prodctionQ.add(indiv_Q);
                well_pipenet_match.add(well_pipe_macthing(indiv_Q, Qmax));
                well_match.add(StaticDataMap7.Q_best.get(p) / Qmax);
            }
        }
        db.UpdateData("Well", "Well_individually_product_Q", individual_well_prodctionQ, "Name", all_well_list);
        db.UpdateData("Well", "Well_Pipenet_Matching", well_pipenet_match, "Name", all_well_list);
        db.UpdateData("Well", "Well_Matching", well_match, "Name", all_well_list);
    }

    /**
     * 其他井无产量的时候，只有管网对单井造成影响，评价其影响程度
     */
    private double q(double a, double b, double sum_CL, double Pr, double Pstation) {//计算井的单独产量
        double x = (-a + Math.pow(a * a - 4 * (b + sum_CL) * (Pstation * Pstation - Pr * Pr), 0.5)) / 2.0 / (b + sum_CL);
        return x;
    }

    /**
     * 计算井与管网的匹配性，
     */
    private double well_pipe_macthing(double indiviQ, double qmax) {
        double rate;
        if (indiviQ >= qmax) {
            rate = 1.0;
        } else {
            rate = indiviQ / qmax;
        }
        return rate;
    }

}
