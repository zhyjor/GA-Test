/*
 *
 */
package Model.PipeNet;

import Data.MapStorage.DynamicDataMap8;
import Data.MapStorage.Starter_third;
import Data.MapStorage.StaticDataMap7;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试各条管道的CLQ2值计算
 *
 * @author 武浩
 */
public class Pipe_CLQ2_测试类 {

    static private Pipe_CLQ2 pc;//累加CLQ2
    static private List<String> list;//暂存子树点
    static private Map<String, Double> map;//暂存CLQ2
    static private Map<String, Integer> ID;//点的属性
    private static Starter_third st;

    public static void main(String[] args) {
        st = new Starter_third();
        st.starter3(true);
        pc = new Pipe_CLQ2();
        ID = StaticDataMap7.ID;
        System.out.println("测试类：：：：：需要优化的树结构：：：" + StaticDataMap7.rootofNeedOptiTree);
        for (String o : StaticDataMap7.rootofNeedOptiTree) {
            map = new HashMap();
            list = StaticDataMap7.allPoint_in_childtree.get(o);//获取子树结构全部点
            int num = list.size();//数量
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("测试类:::::::: 本次优化的树结构：root=>" + o + " ::::::::包含的点" + list);
            double Q[] = new double[num];//各个点的产量，无产量就是0
            for (int i = 0; i < num; i++) {
                String name = list.get(i);
                if (ID.get(name) == 0) {
                    Q[i] = DynamicDataMap8.WellQ.get(name);
                } else {
                    Q[i] = 0;
                }
            }
            map = pc.pipe_CLQ2(o, list, Q);
            System.out.println("点 " + list + " 的流量==" + Arrays.toString(Q));
            for (String p : list) {
                if (ID.get(p) == 0) {
                    double data = map.get(p);
                    System.out.println("CLQ2 of  " + p + "======：：：" + data);
                }
            }
        }
    }
}
