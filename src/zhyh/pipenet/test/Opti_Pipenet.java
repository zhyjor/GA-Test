/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.pipenet.test;

import zhyh.Data.MapStorage.StaticDataMap7;


import zhyh.pipenet.Func.Opti_Result_Storager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhyh
 */
public class Opti_Pipenet {
    public static String target_Opti;//本次优化的膜牧目标
    public static List<String> well_in_childtree;
    public static List<String> allpoint_in_childtree;
    public static double[] Qmax;//目标点集的产量上限，用于GAdecoder类中
    public static double[] Qmin;//目标点集的产量下限，用于GAdecoder类中
    private Opti_Algorithm ss;
    private Opti_Result_Storager ors;
    public static boolean output = true;//转为评价功能而设置的，组织结果输出至数据库
    public static List<String> well_for_estimate;//评价功能，专为评价单井的影响而设置
    
    public static Map<String, Double> wellholePmin;
    public static Map<String, Double> wellholePmax;

    public Opti_Pipenet() {
        well_for_estimate = new ArrayList();
        Qmax = new double[1];
        Qmin = new double[1];
        well_in_childtree = new ArrayList();
        allpoint_in_childtree = new ArrayList();
        ors = new Opti_Result_Storager();
        ss = new Opti_Algorithm();
       
    }

    /**
     * 计算各个井的产量上下线
     */
    private void input(List<String> tree_well) {
        int num = tree_well.size();//得到子树中的全部井.size();
        Qmax = new double[num];
        Qmin = new double[num];
        for (int i = 0; i < num; i++) {
            String name = tree_well.get(i);
            if (well_for_estimate.size() != 0 && well_for_estimate.contains(name)) {
                Qmax[i] = 0;
                Qmin[i] = 0;
            } else {
                Qmax[i] = StaticDataMap7.Q_T_max.get(name);
                Qmin[i] = StaticDataMap7.Q_T_min.get(name);
            }

        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("zhyh-Opti_Pipenet:>>>> " + tree_well + " >>>优化变量范围：" + Arrays.toString(Qmin) + "=>" + Arrays.toString(Qmax));
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }

    public void optstart() throws InterruptedException {//针对管网进行优化

        for (String o : StaticDataMap7.rootofNeedOptiTree) {
            target_Opti = o;
            well_in_childtree = StaticDataMap7.well_in_childtree.get(o);
            allpoint_in_childtree = StaticDataMap7.allPoint_in_childtree.get(o);
            input(well_in_childtree);
            ss = new Opti_Algorithm();//优化算法
            ss.start();
            Opti_Result_Storager.Q = ss.finalResult_X();
            Opti_Result_Storager.welllist = well_in_childtree;
            Opti_Result_Storager.allpointlist = allpoint_in_childtree;
            ors.storageResult();
        }
        if (output) {
            ors.output_to_database();
        }
        System.out.println("");
        System.out.println("。。。。。。。。。。。。。。。。。zhyh-优化结束！。。。。。。。。。。。。。。。。。。");
    }

    public static void main(String[] args) {
        List t = new ArrayList();
        System.out.println(t.size());
    }
}
