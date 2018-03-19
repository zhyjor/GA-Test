/*
 * 本类的作用是将油田整体树结构中需要优化的部分分离出来，即：进集气站或者增压装置之前的子树结构分离出来作为单独需要优化的个体
 */
package zhyh.Data.CreateTree;

import zhyh.Data.InputandClassify.NameList4;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将气田管网划分成需要优化小型树状单元，以主动增压点的上游树结构为基本单元
 *
 * @author 武浩
 */
public class ChildrenTree_NeedOptim {

    private Map<String, Integer> ID;//类型：0井、1阀组、2集气站、3中央处理厂
    private Map<String, List<String>> comemap; //各个管道来流方向
    private Map<String, String> gomap; //各个管道流出方向
    private Map<String, List<String>> allbelongmap;//某点的全部上游点

    public static List<String> rootofNeedOptiTree;//存储要优化的子树根
    public static Map<String, List<String>> gotorootPath;//各个井到子树root的路径

    public ChildrenTree_NeedOptim() {
        rootofNeedOptiTree = new ArrayList();
        gotorootPath = new HashMap();
        ID = NameList4.ID;
        comemap = BuildTree5.Comemap;
        gomap = BuildTree5.Gomap;
        allbelongmap = ChildrenMap6.Allbelongmap;
    }

    protected void init() {//构造方法，初始化上面的参数，并启动
        rootofNeedOptiTree = new ArrayList();
        gotorootPath = new HashMap();
        System.out.println("");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        the_whole();//启动
        System.out.println("UnitofNeedOptim9:需要优化的树根为：" + rootofNeedOptiTree);
        System.out.println("》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》");
    }

    /**
     * 判断需要优化的子树结构root点， 统计各口井到增压点的路径
     * 判断该点如果是增压站等增压点，就遍历其上游的邻接点，然后去除其中的主动增压点，将剩下的点保存为root点
     * 如果判断该点是井，就计算并保存其到增压站的路径。
     */
    private void OptiTarget(String point) {

        List<String> arroundPointNeedOpt = new ArrayList();//要优化的站的周围的点中，非主动增压的点
        if (ID.get(point) > 1) {//遍历各个站等主动增压点
            arroundPointNeedOpt.addAll(comemap.get(point));//该站周围需要优化的点，如果是其他站或者增压点，就略过
            List<String> temp = new ArrayList();
            for (String o : arroundPointNeedOpt) {//去除上游点中的主动增压点
                if (ID.get(o) > 1) {
                    temp.add(o);
                }
            }
            arroundPointNeedOpt.removeAll(temp);
            rootofNeedOptiTree.addAll(arroundPointNeedOpt);
        }
        if (ID.get(point) == 0) {//如果该点是井
            List<String> path = new ArrayList();
            path.add(point);
            String nextgo = point;
            String temp;//流出方向
            while (true) {
                temp = gomap.get(nextgo);
                nextgo = temp;
                if (ID.get(nextgo) > 1) {
                    path.add(nextgo);
                    break;
                }
                path.add(temp);
            }
            gotorootPath.put(point, path);
        }
    }

    /**
     * 启动方法， 遍历整个气田的全部点，逐个判断
     */
    private void the_whole() {
        for (String o : NameList4.namelistPipe) {
            OptiTarget(o);
        }
    }

}
