/*
 * 本包利用管道端点判断各个点之间的连接关系. 
 * 这里只能判断与某个点邻接的所有点的名称,Arround(String,[arroundNode])
 *
 * @author 武浩
 */
package Data.CreateTree;

import Data.InputandClassify.NameList4;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 划分每个点周围的来流点与流出点，点在树结构中的深度等
 * 运行次序：5
 * @author 武浩
 */
public class BuildTree5 {

    public static Map<String, List<String>> Comemap;//各个点的来流方向
    public static Map<String, String> Gomap;//各个点的流出方向
    public static Map<String, Integer> Depthmap;//各个点的树结构中的深度
    public static int Maxdeep;//树结构最大深度
    public static List<String> LeafNode;//叶节点
    private ArroundNode arroundnode;
    public static Map<String, List<String>> arroundmap;//取得单点周围所有点的map
    private Map<String, Integer> depth;//定义点的深度map

    public BuildTree5() {
        arroundnode = new ArroundNode();
    }

    public void Fuzhi() {  //这里给come，go,Depthmap赋值,
        depth = new HashMap();
        arroundmap = arroundnode.Arround();
        LeafNode = new ArrayList();
        Comemap = NoComemap();//附初值，此时为空
        Gomap = new HashMap();//附初值，此时为空
        int deep = 0;//深度计数
        RootNode Root = new RootNode();//取得树结构的根节点
        List<String> root = Root.Root();//root初值，油流终点  
        String go;//某个root点流出方向
        List<String> arround;//某个root点周围所有点
        List<String> comelist = null;//某个root点come点集合
        List<String> come;//come点集合
        while (!root.isEmpty()) {    //遍历root点
            comelist = new ArrayList();//某个root点come点集合
//            System.out.println("构造树的root=" + root);
            for (String o : root) {    //为come赋值，由ROOT点开始循环判断，广度优先
                arround = new ArrayList();
                depth.put(o, deep);//为点写入深度，
                come = new ArrayList();//come点集合
                arround.addAll(arroundmap.get(o));  //目标周围点的集合  
                go = Gomap.get(o);         //找出其go值
                arround.remove(go);//将go点从周围的点中移除，剩下的就是come即来流的方向
                come = arround;            //come=all-go
                if (come.size() == 0) {//判断该点无来流，作为叶节点
                    LeafNode.add(o);
                }
                comelist.addAll(come);//将come点集中放入一个集合
                Comemap.put(o, come);//建立map关系
                for (String co : come) {//遍历全部come点并给come点写入go 
                    Gomap.put(co, o);
                }
            }
            Maxdeep = deep++;
            root = new ArrayList();
            root.addAll(comelist); //将本次循环的come点集作为下次循环的ROOT点
        }
        Depthmap = depth;//为Depthmap赋值

    }

    /**
     * 纯粹是初始化用的
     */
    private Map<String, List<String>> NoComemap() {           //没有值
        Map map = new HashMap();
        for (String o : NameList4.namelistPipe) {
            map.put(o, new ArrayList());
        }
        return map;
    }
}
