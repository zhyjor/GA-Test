/*
 *树结构的查询类
 */
package zhyh.Data.CreateTree;

import zhyh.Data.InputandClassify.NameList4;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 武浩
 */
public class ChildrenMap6 {

    public static Map<String, List<String>> Allbelongmap;//全部子节点
    public static Map<String, List<String>> AllbelongWell;//所属的井列表
    private static Map<String, List<String>> comemap;//父节点
    private static Map<String, Integer> ID = NameList4.ID;

    public void init() { //构造方法
        AllbelongWell = new HashMap();
        comemap = BuildTree5.Comemap;
        Allbelongmap = AllBelong();    //给Allbelongmap赋值
//        System.out.println("ChildrenMap6:树结构构建完毕");
//        System.out.println("");
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private Map<String, List<String>> AllBelong() {//任意点全部上游点map

        Map<String, List<String>> map = new HashMap();
        for (String o : NameList4.namelistPipe) {
            map.put(o, Bianli(o));

        }
//        System.out.println("FZ-1的children:" + map.get("FZ-1"));
//        System.out.println("集气1站的well:" + AllbelongWell.get("集气1站"));
        return map;
    }

    private List<String> Bianli(String name) {  //统计树结构某个点下所属所有上游点children
        List<String> welllist = new ArrayList();
        List<String> come = comemap.get(name);//获取该点的[come]初值
        List<String> comelist = new ArrayList();   //所属全部下属点children的集合

        List<String> root = come;          //本次循环的起点
        comelist.addAll(come);

        while (!root.isEmpty()) {
            List<String> temp = new ArrayList();  //统计本次循环的全部come，每次循环复写
            for (String o : root) {      //遍历Root点
                come = comemap.get(o);   //找到[come]
                if (ID.get(o) == 0) {
                    welllist.add(o);
                }
                comelist.addAll(come);   //统计全部come点，无复写
                temp.addAll(come);       //统计全部come点，每次循环复写
            }
            root = temp;                 //下次循环的起点
        }
        AllbelongWell.put(name, welllist);
        return comelist;
    }

}
