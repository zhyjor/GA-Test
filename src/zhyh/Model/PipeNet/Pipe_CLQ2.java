/*
 * 这是本包各个类的功能汇集类，最终要累加各个井到主动增压点经过的各条管道的CLQ^2值
 */
package zhyh.Model.PipeNet;

import zhyh.Data.MapStorage.DynamicDataMap8;
import zhyh.Data.MapStorage.StaticDataMap7;
import zhyh.Background_functions.Key2_Opti;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 管网计算模型，输入需要优化的树结构根节点，以及树结构各个点的产量
 *
 * @author 武浩
 */
public class Pipe_CLQ2 {

    private Map<String, Double> pipeQmap;//要优化的目标点集所属的树结构中，各个管道的流量
    private List<String> optiTargetlist;//要优化的目标点集,allbelong+自己
    private String station;
    private double stationP;
    private double stationP2;
    private String Root;
    private Map<String, Double> PipeCLMap;//全部管道的CL值
    private Map<String, Double> PipeCLQ2map;//统计各个井到增压站的路径CLQ^2累加值并加上终点站进站压力平方，搜索方法：输入井的名字,
    private Q_net_Cumulator qc;

    public Pipe_CLQ2() {
        pipeQmap = new HashMap();
        qc = new Q_net_Cumulator();
    }

    /**
     * 输入要优化的点集，必须是一个单元树结构的根节点;输入该树结构的根节点，全部点，以及各个点的流量,该方法用于Link_Pipenet_model.pipeCLQ2map()中
     * 本Map中只有井开始的路径的累加值，其他点都是null 查询方法：输入管道起点名
     */
    public Map<String, Double> pipe_CLQ2(String root, List<String> pointlist, double[] Q) {//Q单位应为：m3/d

        Root = root;
        optiTargetlist = pointlist;
        PipeCLQ2map = new HashMap();
        station = StaticDataMap7.Gomap.get(Root);
        stationP = (DynamicDataMap8.pipe_OutP.get(station) + 0.1) * 1000000.0;//最终到增压点的压力,Pa
        stationP2 = stationP * stationP;
        pipeQmap = qc.input_Name_Q_list(pointlist, Q);//统计各条管道的流量
        PipeCLMap = DynamicDataMap8.Pipe_CL;
        if (Key2_Opti.True_or_Ideal == true) {//是否使用现场数据
            PipeCLMap = DynamicDataMap8.Real_Pipe_CL;
        }
        SumCLQ2();
        return PipeCLQ2map;

    }

    /**
     * 累加各井到终点的路径上各条管道的CQ^2,
     */
    private void SumCLQ2() {

        double leijia = 0;
        String pipe;//管道名称
        String point;
        double CL;
        double Q;
        List<String> path;
        for (String o : optiTargetlist) {//遍历各个点
            if (StaticDataMap7.ID.get(o) == 0) {//筛选出井
                leijia = 0;
                path = StaticDataMap7.gotorootPath.get(o);
                int num = path.size();
                for (int i = 0; i < num - 1; i++) {//遍历各个点的各条路径,减一是去掉最后一个点，集气站
                    point = path.get(i);
                    pipe = point + path.get(i + 1);
                    CL = PipeCLMap.get(pipe);
                    Q = pipeQmap.get(pipe) / 86400.0;//m3/s
                    leijia = leijia + CL * Q * Q;
                }
                leijia = leijia + stationP2;
                PipeCLQ2map.put(o, leijia / Math.pow(10, 12));//转回MPa^2
            }
        }
    }
}
