/*
 * 本包利用管道端点判断各个点之间的连接关系. 
 * 这里只能判断与某个点邻接的所有点的名称,Arround(String,[arroundNode])
 *
 * @author 武浩
 */
package zhyh.Data.CreateTree;

import zhyh.Data.InputandClassify.NameList4;
import java.util.ArrayList;
import java.util.List;
import zhyh.Data.InputandClassify.StaticData2;

/*
 * 获取井网的根节点，便于构建树结构
 *
 * @author 武浩
 */
public class RootNode {//构建树结构的循环起点（一般为中央处理厂）

    private List<String> rootname;
    /*
     * Namedata(String biaoname)读取各个工作表名称
     * Root()判断油流终点，一般为中央处理厂，list
     * Root()返回根节点集合
     */

    protected List<String> Root() {
        List<String> root = new ArrayList();
        if (root(StaticData2.getCenterStationName())) {
        } else if (root(StaticData2.getStationName())) {
        } else {
            root(StaticData2.getValveName());
        }
//        System.out.println("根节点为：" + rootname);
        return rootname;
    }

    private boolean root(List<String> list) {
        rootname = new ArrayList();
        for (String o : list) {
            if (NameList4.namelistPipe.contains(o)) {
                rootname.add(o);
            }
        }
        return rootname.size() != 0;
    }
}
