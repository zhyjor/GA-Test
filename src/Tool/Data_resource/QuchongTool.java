/*
 * 本包利用管道端点判断各个点之间的连接关系. 
 * 这里只能判断与某个点邻接的所有点的名称,Arround(String,[arroundNode])
 *
 * @author 武浩
 */
package Tool.Data_resource;

import java.util.HashSet;
import java.util.List;

/*
 * Quchong（List list）去除list中的重复值,以及"";
 *
 * @author 武浩
 */
public class QuchongTool {

    public List<String> Quchong(List<String> list) {                             //去除list中的重复值,以及"";

        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        for (String o : list) {
            if (o == "") {
                list.remove(o);
            }
        }
        return list;
    }

}
