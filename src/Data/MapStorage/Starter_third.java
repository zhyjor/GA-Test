/*
 * 数据处理类的启动方法类，包括Data.。。。三个包，其他功能的使用均建立在这个模块的正常运行上
 */
package Data.MapStorage;

import Data.CreateTree.Starter_second;

/**
 * 本类的启动方法
 *
 * @author 武浩
 */
public class Starter_third {

    private StaticDataMap7 staticdatamap;
    private DynamicDataMap8 dynamicdata;//为动态参数赋值
    private Starter_second ss;

    public void starter3(boolean show) {
        ss = new Starter_second();
        ss.starter2(show);
        staticdatamap = new StaticDataMap7();
        staticdatamap.init();
        dynamicdata = new DynamicDataMap8();
        dynamicdata.init();
        ChildrenTree_NameList.init();
        StaticDataMap7.allPoint_in_childtree = ChildrenTree_NameList.Point_in_childtree;
        StaticDataMap7.well_in_childtree = ChildrenTree_NameList.Well_in_childtree;
    }
//
    public static void main(String[] args) {
        new Starter_third().starter3(true);
        for (String o : StaticDataMap7.rootofNeedOptiTree) {
            System.out.println("allpoint in children tree" + StaticDataMap7.allPoint_in_childtree.get(o));
            System.out.println("allwell in children tree" + StaticDataMap7.well_in_childtree.get(o));
        }
    }
}
