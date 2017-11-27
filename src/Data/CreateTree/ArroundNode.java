 /*
 * 本包是用来生成树结构的，将数据库表中的点与点之间的连接关系连成树结构.
 * Show类可以显示生成的树结构.
 * 在生成树结构的同时，将点之间的关系存入EachMap中，方便其他计算模型的调用查找。
 */
package Data.CreateTree;

import Tool.Data_resource.QuchongTool;
import Data.InputandClassify.NameList4;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Data.InputandClassify.StaticData2;

/*
 * 本类利用管道端点判断各个点之间的连接关系. 
 * 这里只能判断与某个点邻接的所有点的名称,Arround(String,[arroundNode])
 *
 * @author 武浩
 */
public class ArroundNode {

    private QuchongTool tool;

    public ArroundNode() {
        tool = new QuchongTool();
    }

    protected Map<String, List<String>> Arround() {//返回各个点的邻接点，输入名称查询邻接点List

        List<String> list1 = StaticData2.getPipeStartPoint();//读取管道一个端点
        List<String> list2 = StaticData2.getPipeEndPoint();//读取管道另一个端点
        Map<String, List<String>> map = new HashMap();//放入全部点的名称
        List<String> arround1;
        List<String> arround2;
        List<String> quchong = new ArrayList();//去重后得集合

        for (String o : NameList4.namelistPipe) {
            map.put(o, new ArrayList());
        }                               //为map附初值（空集合）
        int max = list1.size();//管道的数量

        for (int i = 0; i < max; i++) {  //赋值，将每个点邻接点放入他的邻接集

            arround1 = map.get(list1.get(i));//获得左边一列某个点的邻接点集
            arround1.add(list2.get(i));      //将相邻点加入集合
            map.put(list1.get(i), arround1); //再将集合放回map中
            arround2 = map.get(list2.get(i));//右边一列的过程与上相同
            arround2.add(list1.get(i));
            map.put(list2.get(i), arround2);
        }
        for (String o : NameList4.namelistPipe) {//去掉list中的重复值
            quchong = tool.Quchong(map.get(o));
            map.put(o, quchong);
        }
//        System.out.println("ArroundNode:集气1站周围" + map.get("集气1站"));
        return map;
    }

}
