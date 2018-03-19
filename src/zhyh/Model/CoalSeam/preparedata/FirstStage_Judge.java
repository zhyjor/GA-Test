/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Model.CoalSeam.preparedata;

import java.util.List;

/*
 * 根据生产历史数据判断是否是排水阶段（第一阶段）
 *《多因素影响下煤层气井生产初期合理排水量确定》——刘晓燕，等；
 * @author 武浩
 */
public class FirstStage_Judge {

    /**
     * 下面的需要历史生产数据
     */
    public List<Double> waterQlist;//产水历史生产数据
    public List<Double> gasQlist;//产气历史生产数据
    private double waterQmax;//最大产水量
    private double gasQmax;//最大产气量
    private double newwaterQ;//最近产水
    private double newgasQ;//最近产气
    private double D;//套管直径
    private double D1;//油管内径
    private double D2;//光杆直径
    private double miuw = 0.0000179;//水的粘度，Pa.s
    private double rous = 1.5;//煤粉密度
    private double roul = 1.0;//水密度

    /**
     * 该方法判断最大值
     */
    private double max(List<Double> list) {
        double max = 0;
        for (double o : list) {
            if (o > max) {
                max = o;
            }
        }
        return max;
    }

    /**
     * 判断是否是第一阶段
     */
    public boolean firstStage() {
        boolean TF = false;
        double waterQmax = max(waterQlist);
        double gasQmax = max(gasQlist);
        newwaterQ = waterQlist.get(waterQlist.size() - 1);
        newgasQ = gasQlist.get(gasQlist.size() - 1);
        double niu = newgasQ / gasQmax / (newgasQ / gasQmax + newwaterQ / waterQmax);
        if (niu >= 0.25) {
            TF = true;
        }
        return TF;
    }

    /**
     * 计算最低临界水速，携带煤粉流速
     */
    private double vs0(double ds) {
        double vs0 = 0.2 * (1 - ds * ds / D / D) * (Math.pow(ds, 1.18) * Math.pow((rous - roul) * 9.8, 0.72)) / Math.pow(roul, 0.27) / Math.pow(miuw, 0.45);
        return vs0;
    }

    /**
     * 第一阶段最小产量，携煤粉产量
     */
    public double Qmin(double ds) {
        double v = vs0(ds);
        return 3.14 * v * Math.abs(D1 * D1 - D2 * D2);
    }

}
