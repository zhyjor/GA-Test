/*
 * 本包只存储CreatTree与DataManager中产生的必要数据，包括生产数据、树结构、连接关系等，均已Map的形式存储，方便后面的模型调用
 */
package zhyh.Data.MapStorage;

import zhyh.Tool.Data_resource.DBcontroller;
import zhyh.Data.InputandClassify.DynamicData3;
import zhyh.Data.InputandClassify.StaticData2;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态数据的读入,准备运算
 *
 * @author 武浩
 */
public class DynamicDataMap8 {

    private List<String> start;//管道起点列表
    private List<String> end;//管道终点列表
    private StaticData2 sdata;
    private DynamicData3 ddata;

    /**
     * 各个点的实时数据存储map,key=点的名称
     */
    public static Map<String, Double> WellQ;//井口流量,m3/d
    public static Map<String, Double> WellHP;//井口回压，MPa
    public static Map<String, Double> WellZD;//油嘴直径,mm
    public static Map<String, Double> WellT;//井口温度,℃
    public static Map<String, Double> Well_Pwf;//井底流压，MPa
    public static Map<String, Double> WellTP;//井底套压，MPa
    public static Map<String, Double> WellPr;//地层，MPa

    public static Map<String, Double> ValveQ;//阀组流量,m3/d
    public static Map<String, Double> ValveP;//阀组压力，MPa
    public static Map<String, Double> ValveT;//阀组温度,℃

    public static Map<String, Double> StationQ;//集气站流量,m3/d
    public static Map<String, Double> StationInP;//集气站进站压力，MPa
    public static Map<String, Double> StationOutP;//集气站出站压力，MPa
    public static Map<String, Double> StationInT;//集气站进站温度,℃
    public static Map<String, Double> StationOutT;//集气站出站温度,℃

    public static Map<String, Double> CStationInP;//中央处理厂进站压力，MPa
    public static Map<String, Double> CStationOutP;//中央处理厂出站压力，MPa
    public static Map<String, Double> CStationInT;//中央处理厂进站温度,℃
    public static Map<String, Double> CStationOutT;//中央处理厂出站温度,℃
    public static Map<String, Double> CStationQ;//中央处理厂流入流量,m3/d

    public static Map<String, Double> EquipInP;//中央处理厂进站压力，MPa
    public static Map<String, Double> EquipOutP;//中央处理厂出站压力，MPa
    public static Map<String, Double> EquipInT;//中央处理厂进站温度,℃
    public static Map<String, Double> EquipOutT;//中央处理厂出站温度,℃

    /**
     * 管道实时参数map，key=起点+终点，如：管道起点名称“风39-4”，终点名称“风56-9”，key=“"风39-4风56-9"”(String类型)
     */
    public static Map<String, Double> RealQ;//管道测得真实流量,m3/d
    public static Map<String, Double> To;//管道周围环境温度,℃
    public static Map<String, Double> Pipe_trans_rate;//管道输送效率
    public static Map<String, Double> Opti_trans_rate;//管道优化后的输送效率
    public static Map<String, Double> Pipe_CL;
    public static Map<String, Double> Real_Pipe_CL;
    public static Map<String, Double> Opti_pipeQ;

    /**
     * 地层数据查询
     */
    public static Map<String, Double> CoalSeamNowPorosity;//目前的孔隙度，%
    public static Map<String, Double> CoalSeamNowPermeability;//目前的渗透率，%
    public static Map<String, Double> NowPmap;//目前地层压力，MPa

    /**
     * 管道参数
     */
    public static Map<String, Double> pipe_InP;
    public static Map<String, Double> pipe_OutP;
    public static Map<String, Double> pipe_InT;
    public static Map<String, Double> pipe_OutT;

    /**
     * 赋值并进行单位换算
     */
    public void init() {
        sdata = new StaticData2();
        ddata = new DynamicData3();

        pipe_InP = new HashMap();
        pipe_OutP = new HashMap();
        pipe_InT = new HashMap();
        pipe_OutT = new HashMap();

        start = StaticDataMap7.PipeStartPointList;
        end = StaticDataMap7.PipeEndPointList;

        Well_Pwf = DataMap(StaticData2.getWellName(), ddata.getWellLP());
        WellTP = DataMap(StaticData2.getWellName(), ddata.getWellTP());
        WellPr = DataMap(StaticData2.getWellName(), DynamicData3.WellPr);
        WellQ = DataMap(StaticData2.getWellName(), ddata.getWellQ());
        WellHP = DataMap(StaticData2.getWellName(), ddata.getWellHP());
        WellZD = DataMap(StaticData2.getWellName(), ddata.getWellzuiD());
        WellT = DataMap(StaticData2.getWellName(), ddata.getWellT());

        ValveQ = DataMap(StaticData2.getValveName(), ddata.getValveQ());
        ValveP = DataMap(StaticData2.getValveName(), ddata.getValveP());
        ValveT = DataMap(StaticData2.getValveName(), ddata.getValveT());

        StationQ = DataMap(StaticData2.getStationName(), ddata.getStationQ());
        StationInP = DataMap(StaticData2.getStationName(), ddata.getStationComeP());
        StationOutP = DataMap(StaticData2.getStationName(), ddata.getStationGoP());
        StationInT = DataMap(StaticData2.getStationName(), ddata.getStationComeT());
        StationOutT = DataMap(StaticData2.getStationName(), ddata.getStationGoT());

        CStationInP = DataMap(StaticData2.getCenterStationName(), ddata.getCenterStationComeP());
        CStationOutP = DataMap(StaticData2.getCenterStationName(), ddata.getCenterStationGoP());
        CStationInT = DataMap(StaticData2.getCenterStationName(), ddata.getCenterStationComeT());
        CStationOutT = DataMap(StaticData2.getCenterStationName(), ddata.getCenterStationGoT());
        CStationQ = DataMap(StaticData2.getCenterStationName(), ddata.getCenterStationQ());

        EquipInP = DataMap(StaticData2.getEquipmentName(), ddata.getEquipmentComeP());//中央处理厂进站压力
        EquipOutP = DataMap(StaticData2.getEquipmentName(), ddata.getEquipmentGoP());//中央处理厂出站压力
        EquipInT = DataMap(StaticData2.getEquipmentName(), ddata.getEquipmentComeT());//中央处理厂进站温度
        EquipOutT = DataMap(StaticData2.getEquipmentName(), ddata.getEquipmentGoT());//中央处理厂出站温度

        CoalSeamNowPorosity = DataMap(StaticData2.getCoalSeamName(), DynamicData3.CoalNowPorosity);
        CoalSeamNowPermeability = DataMap(StaticData2.getCoalSeamName(), DynamicData3.CoalNowPermeability);
        NowPmap = DataMap(StaticData2.getCoalSeamName(), DynamicData3.NowP);

        IOTP();//管道进出口压力搜索map赋值
        pipe_data();//管道流量搜索map赋值
    }

    /**
     * 数据转化为标准单位后转存Map,本类参数的赋值语句
     */
    private Map<String, Double> DataMap(List<String> listname, List<Double> data) {
        if (listname.size() != data.size()) {
            System.out.println("输入的listname:" + listname);
            System.out.println("输入的data:" + data);
            System.out.println("输入的listname个数=" + listname.size());
            System.out.println("输入的data个数=" + data.size());
            System.out.println("输入的名称数量与数据数量不相同！");
        }
        Map<String, Double> map = new HashMap();
        int min = listname.size();
        double p;
        for (int i = 0; i < min; i++) {
            map.put(listname.get(i), data.get(i));
        }
        return map;
    }

    /**
     * 管道周围环境温度以及流量map赋值语句
     */
    private void pipe_data() {
        Pipe_CL = new HashMap();
        Real_Pipe_CL = new HashMap();
        Pipe_trans_rate = new HashMap();
        Opti_trans_rate = new HashMap();
        Opti_pipeQ = new HashMap();
        Map<String, Double> Qmap = new HashMap();
        Map<String, Double> Tomap = new HashMap();
        List<Double> Q = ddata.getPipeQ();//原始数据中的流量
        List<Double> to = ddata.getPipeTo();//环境温度
        int num = start.size();//管道数量
        String qidian;//起点
        String zhongdian;//终点
        double q;//流量
        double t;//温度
        for (int i = 0; i < num; i++) {
            qidian = start.get(i);
            zhongdian = end.get(i);
            q = Q.get(i);
            t = to.get(i);
            Qmap.put(qidian + zhongdian, q);
            Tomap.put(qidian + zhongdian, t);
            Pipe_trans_rate.put(qidian + zhongdian, DynamicData3.Real_trans_rate.get(i));
            Opti_trans_rate.put(qidian + zhongdian, DynamicData3.Opti_trans_rate.get(i));
            Pipe_CL.put(qidian + zhongdian, DynamicData3.PipeCL.get(i));
            Opti_pipeQ.put(qidian + zhongdian, DynamicData3.Opti_PipeQ.get(i));
        }
        RealQ = Qmap;
        To = Tomap;
    }

    /**
     * 这里将管道起点终点T/P存为Map,搜索key（起点+终点名称） 根据点的数据变化，自动更新管道数据
     */
    private void IOTP() {//管道进出口压力搜索与更新

        DBcontroller control = new DBcontroller();
        pipe_InP.putAll(WellHP);
        pipe_InP.putAll(ValveP);
        pipe_InP.putAll(StationOutP);
        pipe_InP.putAll(CStationOutP);
        pipe_InP.putAll(EquipOutP);

        pipe_InT.putAll(WellT);
        pipe_InT.putAll(ValveT);
        pipe_InT.putAll(StationOutT);
        pipe_InT.putAll(CStationOutT);
        pipe_InT.putAll(EquipOutT);

        pipe_OutT.putAll(WellT);
        pipe_OutT.putAll(ValveT);
        pipe_OutT.putAll(StationInT);
        pipe_OutT.putAll(CStationInT);
        pipe_OutT.putAll(EquipInT);

        pipe_OutP.putAll(WellHP);
        pipe_OutP.putAll(ValveP);
        pipe_OutP.putAll(StationInP);
        pipe_OutP.putAll(CStationInP);
        pipe_OutP.putAll(EquipInP);

    }

}
