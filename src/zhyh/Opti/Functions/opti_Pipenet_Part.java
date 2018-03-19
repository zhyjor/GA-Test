/*
 * 该类是管网模型集中化，转为优化方程，用于Prepare_for_Opti类
 */
package zhyh.Opti.Functions;

import zhyh.Model.PipeNet.Pipe_CLQ2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 武浩
 */
public class opti_Pipenet_Part {

    private List<String> allpointlist;//暂存子树点
    private List<String> allwelllist;//暂存子树点
    private Map<String, Double> map;//暂存CLQ2
    private Map<String, Double> pipeCLQ2map;//各个井到增压站的路径CLQ^2累加值并加上终点站进站压力平方，搜索方法：输入井的名字
    private Pipe_CLQ2 pc;
    private String Root;//本次优化的根节点
    private double[] allpointQ;
    private int wellnum;
    private int allnum;//所有点的数量
    private Map<Integer, Integer> duiyingwei;//供 welllist=>allpointlist 位置快速对应,用于duiyingQ()

    public opti_Pipenet_Part() {
        allpointlist = new ArrayList();
        allwelllist = new ArrayList();
        allpointlist.addAll(OptiAll.allpoint_in_childtree);//获取子树结构全部点
        allwelllist.addAll(OptiAll.well_in_childtree);
        wellnum = allwelllist.size();
        allnum = allpointlist.size();
        allpointQ = new double[allnum];
        duiyingwei(allwelllist, allpointlist);
        pc = new Pipe_CLQ2();
        Root = "";
    }

    /**
     * 返回管网模型方程组的各个y[i]值
     */
    protected double[] functions_part1(String root, double[] Q) {//输入（要优化的树结构的根节点，该树结构的全部井，各个井对应的产量）
        if (Root.equals(root) == false) {//减少重复迭代中的不必要计算
            Root = root;
            System.out.println("");
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println(Thread.currentThread().getName() + "线程=>opti_Pipenet_Part：开始优化" + root + "的子树结构！！");
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("");
        }
        allpointQ = duiyingQ(allwelllist, Q, allpointlist);
//        if (OptiAll.target_Opti.equals("FZ-1")) {
//            System.out.println("");
//            System.out.println("allwelllist" + allwelllist + "=>>>" + Arrays.toString(Q));
//            System.out.println("allpointlist" + allpointlist + "=>>>" + Arrays.toString(allpointQ));
//            System.out.println("");
//        }
        pipeCLQ2map = pc.pipe_CLQ2(root, allpointlist, allpointQ);
        int num = allwelllist.size();
        double[] y = new double[num];
        String point;
        for (int i = 0; i < num; i++) {//遍历全部井,将井的CLQ2摘出来
            point = allwelllist.get(i);
            double temp = pipeCLQ2map.get(point);
            y[i] = deltaP(temp, point);
        }
        return y;
    }

    /**
     * 这个方法相当于井筒模型，计算的是井筒附加压力，利用井口压力推算井底压力，条件是井筒中无水
     */
    private double deltaP(double y, String well) {
        double Y = Math.pow(y, 0.5);
        double Pmax = OptiAll.wellholePmax.get(well);
        double Pmin = OptiAll.wellholePmin.get(well);
        double deltaP = (Pmax - Pmin) * (Y - 0.2) / 9.8 + Pmin;
        double P = Y + deltaP;
        return P * P;
    }

    /**
     * welllist与allpointlist中点的位置一一对应
     */
    private void duiyingwei(List<String> welllist, List<String> allpointlist) {
        int jishu = 0;//由于welllist与allpointlist中共有的点顺序相同，可不必每次从头遍历
        String name1;
        String name2;
        duiyingwei = new HashMap();

        Well_round://循环名，遍历井的循环************
        for (int j = 0; j < wellnum; j++) {
            name1 = welllist.get(j);
            Allpoint_round://循环名，遍历全部点的循环*********
            for (int i = jishu; i < allnum; i++) {
                jishu++;//下次循环的起点
                name2 = allpointlist.get(i);
                if (name1.equals(name2)) {
                    duiyingwei.put(j, i);
                    continue Well_round;
                }
            }
        }
    }

    /**
     * 由于优化部分functions_part1（）中填入的是全部"井"以及产量，而pipe_CLQ2（）中需要全部"点"——包括井、阀组、集气站等——的列表以及产量，需要对应转化一下
     */
    private double[] duiyingQ(List<String> welllist, double wellQ[], List<String> allpointlist) {

        double allQ[] = new double[allnum];
        String name;
        for (int i = 0; i < wellnum; i++) {
            name = welllist.get(i);
            allQ[duiyingwei.get(i)] = wellQ[i];
        }
        return allQ;
    }

}
