/*
 * 用于Updata_database_Pwf_Q_Constraint类中
 */
package zhyh.Model.CoalSeam.Qmax_Qmin;

import zhyh.Data.InputandClassify.NameList4;
import zhyh.Data.MapStorage.StaticDataMap7;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 武浩
 */
public class Link_Coalseam_Model {

    /**
     * 下面这些变量需要外部赋值
     */
    private Map<String, List<String>> allbelongmap; //各个点全部子节点
    private Map<String, Integer> ID;//点的身份认证：0为井，1为阀组，2为集气站,3为中央处理厂

    private CoalSeam_Qmax_Qmin cqq;
    public Map<String, Double> Qmax;//各个井的最大产量
    public Map<String, Double> Qmin;//各个井的最小产量
    public Map<String, Double> Pwf_max;//各个井的最大产量
    public Map<String, Double> Pwf_min;//各个井的最小产量

    /**
     * 要先运行这个才有Qmax,Qmin的值，用于Starter4中
     */
    protected void init() {
        allbelongmap = StaticDataMap7.Allbelongmap;
        ID = StaticDataMap7.ID;
        cqq = new CoalSeam_Qmax_Qmin();
        cqq.input_wellList(NameList4.namelistPipe);
        Qmax = cqq.Qmaxmap;//各个井的最大产量
        Qmin = cqq.Qminmap;//各个井的最小产量
        Pwf_max = cqq.Pmaxmap;//各个井的最大井底压力
        Pwf_min = cqq.Pminmap;//各个井最小井底压力
    }

}
