/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Data.CreateTree;

import zhyh.Data.InputandClassify.Starter_first;
import zhyh.Tool.Shower.InterTree_Auxiliary;
import zhyh.Tool.Shower.InterTree;

/**
 *
 * @author 武浩
 */
public class Starter_second {

    static private BuildTree5 tree;
    static private ChildrenMap6 child;
    static private ChildrenTree_NeedOptim uno;
    private Starter_first sf;

    public void starter2(boolean show) {
        sf = new Starter_first();
        sf.starter();//先启动Starter1,将所有数据读入并存储
        if (show) {
            Starter_second.showTree();
        }
        tree = new BuildTree5();
        tree.Fuzhi();
        child = new ChildrenMap6();
        child.init();
        uno = new ChildrenTree_NeedOptim();
        uno.init();
    }

    /*
     * 树结构显示线程
     */
    static private void showTree() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new InterTree_Auxiliary();
                String[] name = InterTree_Auxiliary.data();//名称
                String[] data = name;
                InterTree.xianshi(name, data);
            }
        }).start();
    }
//    public static void main(String[] args) {
//        new Starter_second().starter2(true);
//    }
}
