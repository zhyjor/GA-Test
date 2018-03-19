/*
 * 根据各个井的产量计算子树结构中各个点的流量
 */
package zhyh.Model.PipeNet;

import zhyh.Data.MapStorage.StaticDataMap7;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供方法input_Name_Q_list（），输入整个子树结构各个点名称以及各个点的产量，没有产量的，如：集气站，阀组等；产量为0。返回值为子树结构中各个点的流经流量，或者点的下游管道中的流量
 *
 * @author 武浩
 */
public class Q_net_Cumulator {

    private Map<String, Double> pipeQmap;//要优化的目标点集所属的树结构中，各个管道的流量
    private Map<String, Double> pointQmap;//存储各个点的产量，便于累加

    /**
     * 输入子树结构各个点名，以及对应的各点产量 根据树结构累加流经各个点的流量，无产量的点产量为0，用于优化算法
     */
    public Map<String, Double> input_Name_Q_list(List<String> name, double[] Q) {

        pipeQmap = new HashMap();
        int length = name.size();
        pointQmap = new HashMap();
        for (int i = 0; i < length; i++) {//各个点产量一一对应
            pointQmap.put(name.get(i), Q[i]);
        }
        for (String o : name) {//累加得到各个管道流量
            qmap(o);
        }
        return pipeQmap;
    }

    /**
     * 根据井各个点产量，更新各个管道中的流量，管道名称：本点名+下游点名
     */
    private void qmap(String point) {
        List<String> comelist = new ArrayList();
        comelist.addAll(StaticDataMap7.Allbelongmap.get(point));
        comelist.add(point);
        double leijia = 0;
        for (String o : comelist) {
            leijia = leijia + pointQmap.get(o);
        }
        pipeQmap.put(point + StaticDataMap7.Gomap.get(point), leijia);
    }

}
