/*
 * InterTree的数据来源类
 */
package zhyh.Tool.Shower;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import zhyh.Tool.Data_resource.DBcontroller;
import zhyh.Data.InputandClassify.NameList4;

/**
 * InterTree的辅助方法类
 *
 * @author 武浩
 */
public class InterTree_Auxiliary {

    static List<String> namelist;
    public static Map map;//给点编号

    public InterTree_Auxiliary() {
        namelist = NameList4.namelistPipe;
        map = new HashMap();

        int i = 0;
        for (String o : namelist) {
            map.put(o, i);
            map.put(i, o);
            i++;
        }
    }

    /**
     * 名称List转数组
     */
    public static String[] data() {
        String[] name = new String[namelist.size()];
        int i = 0;
        for (String o : namelist) {
            name[i] = o;
            i++;
        }
        return name;
    }

    /**
     * 名字序号一一对应
     */
    private static int num(String name) {
        return (int) map.get(name);
    }
    private static DBcontroller data = new DBcontroller();

    public static List<Integer> numlist(String name) {

        ResultSet rest = data.getFile("Pipeline");
        List<String> list1 = data.ReadString(rest, name);
        List<Integer> list2 = new ArrayList();
        for (String o : list1) {
            list2.add(num(o));
        }
        return list2;
    }

    public static List<String> namelist(String name) {
        ResultSet rest = data.getFile("Pipeline");
        List<String> list = data.ReadString(rest, name);
        return list;
    }

}
