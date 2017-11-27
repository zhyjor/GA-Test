/*
 * 本包利用管道端点判断各个点之间的连接关系. 
 * 这里只能判断与某个点邻接的所有点的名称,Arround(String,[arroundNode])
 * 
 运行次序：4
 * @author 武浩
 */
package Data.InputandClassify;

import Tool.Data_resource.QuchongTool;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/*
 * 获得井网所有点的名称列表,管道中的点如果与井阀组等表中的点对不起来的基本处理
 *
 * @author 武浩
 */
public class NameList4 {

    public static Map<String, Integer> ID;

    public static List<String> namelistPipe;//由管道数据获得,一般用这个！！！！
    public static List<String> namelistPoint;//由点数据表获得

    /**
     * 按照表格中的排列顺序读入各个点的名称
     */
    public void refresh() {
        ID = new HashMap();
        namelistPipe = new ArrayList();
        namelistPoint = new ArrayList();
        namelistPipe.addAll(StaticData2.getPipeStartPoint());
        namelistPipe.addAll(StaticData2.getPipeEndPoint());
        namelistPipe = new QuchongTool().Quchong(namelistPipe);

        StaticData2 s = new StaticData2();
        List<String> AllList = new ArrayList();
        List<String>[] list = new ArrayList[4];
        list[0] = StaticData2.getWellName();//井名字
        list[1] = StaticData2.getValveName();//阀组名字
        list[2] = StaticData2.getStationName();//集气站名字
        list[3] = StaticData2.getCenterStationName();//中央处理厂名字
        for (int i = 0; i < 4; i++) {
            for (String o : list[i]) {
                ID.put(o, i);
            }
        }
        AllList.addAll(list[0]);
        AllList.addAll(list[1]);
        AllList.addAll(list[2]);
        AllList.addAll(list[3]);
        namelistPoint.addAll(AllList);
        check();//检查点列表与管道起终点列表名称是否相同
//        System.out.println("NameListPipe:名称列表更新完毕！" + namelistPipe);
//        System.out.println("NameListPoint:名称列表更新完毕！" + namelistPoint);
    }

    /**
     * 检查点的输入与管道起终点是否相同
     */
    private void check() {

        List<String> temp1 = new ArrayList();
        List<String> temp2 = new ArrayList();

        temp1.addAll(namelistPoint);
        temp2.addAll(namelistPipe);
        temp1.removeAll(namelistPipe);
        temp2.removeAll(namelistPoint);

        if (temp1.size() != 0) {
            System.out.println("NameList:这几个点无管道连接！可能的问题：名字里多了空格，或者没有输入！");
            System.out.println(temp1);
            System.out.println("-----------------------------------------------------------------------------------");
        }
        if (temp2.size() != 0) {
            System.out.println("NameList:这几条管道端点无数据！可能的问题：名字里多了空格，或者没有输入！");
            System.out.println(temp2);
            System.out.println("-----------------------------------------------------------------------------------");
        }
    }

}
