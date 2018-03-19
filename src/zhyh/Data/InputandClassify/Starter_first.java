/*
 * 该类是本包的启动类
 */
package zhyh.Data.InputandClassify;

/**
 * 本包的运行启动方法
 *
 * @author 武浩
 */
public class Starter_first {

    static private AllData1 ad;
    static private StaticData2 sd;//包括名字、连接关系等不变量
    static private DynamicData3 dd;//包括温度、压力等常变量
    static private NameList4 nl;

    public void starter() {
        ad = new AllData1();
        sd = new StaticData2();
        dd = new DynamicData3();
        nl = new NameList4();

        ad.UpdateAllData();
        sd.updater();
        dd.updater();
        nl.refresh();
    }
}
