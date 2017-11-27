/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.CoalSeam.Qmax_Qmin;

import Data.MapStorage.StaticDataMap7;
import java.util.Map;

/**
 * 查询各个井的约束条件
 *
 * @author 武浩
 */
public class CoalSeam_Pmax_Pmin {

    private Map<String, Double> c;//煤岩粘聚力
    private Map<String, Double> cr;//残余煤岩粘聚力
    private Map<String, Double> fai;//内摩擦角
    private Map<String, Double> fair;//残余内摩擦角
    private Map<String, Double> md;//常数,实验确定，反映Xd与b3之间的关系；可以直接输入，或者计算获得。******
    private static ReasonableP_WangZhiyin rp;
    private static Map<String, Double> St;//抗拉强度
    private static Map<String, Double> Pr;//抗拉强度
    /**
     * 地层生产压差界限,这是目的，用于CoalSeam_Qmax_Qmin类
     */
    protected double Pmax;
    protected double Pmin;

    public CoalSeam_Pmax_Pmin() {//构造方法

        c = StaticDataMap7.CoalC;//煤岩粘聚力
        cr = StaticDataMap7.CoalCr;//残余煤岩粘聚力
        fai = StaticDataMap7.CoalFai;//内摩擦角
        fair = StaticDataMap7.CoalFair;//残余内摩擦角
        md = StaticDataMap7.mdmap;//常数,实验确定，反映Xd与b3之间的关系；可以直接输入，或者计算获得。******
        rp = new ReasonableP_WangZhiyin();
        St = StaticDataMap7.CoalSt;//抗拉强度
        Pr = StaticDataMap7.Pr;//抗拉强度
    }

    /**
     * 初始化煤层合理生产压力范围数据,输入井名
     */
    protected void input_wellname(String o) {

        double c;//岩石内聚力
        double fai;//内摩擦角
        double cr;//残余岩石内聚力
        double fair;//残余内摩擦角
        double Po;//地层压力
        double St;//单轴抗拉应力
        String well_formation = StaticDataMap7.WellFormatin.get(o);//井所属地层名称

        c = this.c.get(well_formation);//单位转回MPa
        fai = this.fai.get(well_formation);
        cr = this.cr.get(well_formation);
        fair = this.fair.get(well_formation);
        Po = this.Pr.get(o);//注意只有这个是井的名字，因为同一地层也有不同压力
        St = this.St.get(well_formation);

        rp.input(Po, c, fai, cr, fair, St);
        Pmax = rp.Pmid;
        Pmin = rp.Pmin;
    }

}
