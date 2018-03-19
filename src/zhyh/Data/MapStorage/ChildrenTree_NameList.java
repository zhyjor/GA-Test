/*
 * 本类方法输入要优化的子树结构root名，列出各点名称列表，供算法模型以及管网模型统一调用，保证顺序都相同
 */
package zhyh.Data.MapStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该类提供子树结构中全部井的列表
 *
 * @author 武浩
 */
public class ChildrenTree_NameList {

    static private List<String> root;
    static public Map<String, List<String>> Well_in_childtree;
    static public Map<String, List<String>> Point_in_childtree;

    public static void init() {
        root = StaticDataMap7.rootofNeedOptiTree;
        Well_in_childtree = new HashMap();
        Point_in_childtree = new HashMap();
        for (String o : root) {
            Well_in_childtree.put(o, childTree_WellNamelist(o));
            Point_in_childtree.put(o, childTree_Namelist(o));
        }
    }

    /**
     * 输入子树结构root，获得该树结构全部点名称
     */
    static private List<String> childTree_Namelist(String root) {
        List<String> comelist = new ArrayList();
        comelist.addAll(StaticDataMap7.Allbelongmap.get(root));//上游全部点
        comelist.add(root);//加上root点本身
        return comelist;
    }

    /**
     * 子树中全部井
     */
    static private List<String> childTree_WellNamelist(String root) {
        List<String> allpoint = childTree_Namelist(root);
        List<String> well_in_childtree = new ArrayList();
        for (String o : allpoint) {
            if (StaticDataMap7.ID.get(o) == 0) {
                well_in_childtree.add(o);
            }
        }
        return well_in_childtree;
    }
}
