/*
 *本类是将ALLData中读入的数据表进行分类存储的类
 */
package Data.InputandClassify;

import Tool.Data_resource.DBcontroller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 动态数据的集合类, 将AllData中的数据按照列转为List存储
 * 运行次序：3
 *
 * @author 武浩
 */
public class DynamicData3 {

    /**
     * 井口数据
     */
    public static List<String> WellTime;//Time
    public static List<Double> WellQ;//产量，m3/d
    public static List<Double> WellTP;//套压，MPa
    public static List<Double> WellHP;//回压，MPa
    public static List<Double> Well_Pwf;//井底流压，MPa
    public static List<Double> WellGasT;//温度，，℃
    public static List<Double> WellzuiD;//油嘴直径，mm
    public static List<Double> WellPr;//现在地层压力，MPa
    /**
     * 阀组数据
     */
    public static List<String> ValveTime;//Time
    public static List<Double> ValveQ;//Q
    public static List<Double> ValveP;//压力，MPa
    public static List<Double> ValveT;//温度，℃
    /**
     * 集气站数据
     */
    public static List<String> StationTime;//Time
    public static List<Double> StationQ;//Q，m3/d
    public static List<Double> StationComeP;//进站压力，MPa
    public static List<Double> StationGoP;//出站压力，MPa
    public static List<Double> StationComeT;//进站温度，℃
    public static List<Double> StationGoT;//出站温度，℃

    /**
     * 中央处理厂数据
     */
    public static List<String> CenterStationTime;//Time
    public static List<Double> CenterStationQ;//Q，m3/d
    public static List<Double> CenterStationComeP;//进站压力，MPa
    public static List<Double> CenterStationGoP;//出站压力，MPa
    public static List<Double> CenterStationComeT;//进站温度，℃
    public static List<Double> CenterStationGoT;//出站温度，℃

    /**
     * 增压设备数据
     */
    public static List<String> EquipmentTime;//Time
    public static List<Double> EquipmentQ;//Q，m3/d
    public static List<Double> EquipmentComeP;//进口压力，MPa
    public static List<Double> EquipmentGoP;//出口压力，MPa
    public static List<Double> EquipmentComeT;//进口温度，℃
    public static List<Double> EquipmentGoT;//出口温度，℃
    public static List<Double> EquipmentI;//电流,A
    public static List<Double> EquipmentV;//电压,V
    public static List<Double> EquipmentTruePower;//真实功率
    public static List<String> EquipmentCondition;//运转状况

    /**
     * 管道数据
     */
    public static List<String> PipeTime;//Time
    public static List<Double> PipeQ;//实际Q,m3/d
    public static List<Double> PipeTo;//环境温度,℃
    public static List<Double> PipeCL;//优化的常数
    public static List<Double> Real_PipeCL;//优化的常数
    public static List<Double> Real_trans_rate;//管道实际输送效率
    public static List<Double> Opti_trans_rate;//管道优化后输送效率
    public static List<Double> Opti_PipeQ;//管道优化后输送Q

    /**
     * 地层数据
     */
    public static List<String> CoalSeamTime;//Time
    public static List<Double> CoalNowPorosity;//地层孔隙度,%
    public static List<Double> CoalNowPermeability;//地层目前渗透率,mD
    public static List<Double> CumulativeWater;//累计产水量，m3
    public static List<Double> CumulativeGas;//累计产气量,m3
    public static List<Double> NowP;//目前地层压力，MPa

    static private DBcontroller control = new DBcontroller();

    public void updater() {//初始化数据，只运行一次

        /**
         * 井数据
         */
        WellTime = control.ReadString(AllData1.WellSet, "Time");//时间
        setWellQ(control.ReadDouble(AllData1.WellSet, "Q"));//产量，m3/d
        setWellTP(control.ReadDouble(AllData1.WellSet, "CasingP"));//套压，MPa
        setWellHP(control.ReadDouble(AllData1.WellSet, "BackP"));//回压，MPa
        setWellLP(control.ReadDouble(AllData1.WellSet, "FBHP"));//井底流压，MPa
        setWellT(control.ReadDouble(AllData1.WellSet, "T"));//温度,℃
        setWellzuiD(control.ReadDouble(AllData1.WellSet, "ChockD"));//油嘴直径,mmj
        WellPr = control.ReadDouble(AllData1.WellSet, "Pr");//时间
        /**
         * 阀组数据
         */
        setValveTime(control.ReadString(AllData1.ValveSet, "Time"));//时间
        setValveQ(control.ReadDouble(AllData1.ValveSet, "Q"));//流量,m3/d
        setValveP(control.ReadDouble(AllData1.ValveSet, "P"));//压力，MPa
        setValveT(control.ReadDouble(AllData1.ValveSet, "T"));//温度，℃
        /**
         * 集气站数据
         */
        setStationTime(control.ReadString(AllData1.StationSet, "Time"));//时间
        setStationQ(control.ReadDouble(AllData1.StationSet, "Q"));//流量，m3/d
        setStationComeP(control.ReadDouble(AllData1.StationSet, "InletP"));//进站压力，MPa
        setStationGoP(control.ReadDouble(AllData1.StationSet, "OutgoingP"));//出站压力，MPa
        setStationComeT(control.ReadDouble(AllData1.StationSet, "InletT"));//进站温度，℃
        setStationGoT(control.ReadDouble(AllData1.StationSet, "OutgoingT"));//出站温度，℃
        /**
         * 中央处理厂数据
         */
        setCenterStationTime(control.ReadString(AllData1.CenterStationSet, "Time"));//时间
        setCenterStationQ(control.ReadDouble(AllData1.CenterStationSet, "Q"));//流量，m3/d
        setCenterStationComeP(control.ReadDouble(AllData1.CenterStationSet, "InletP"));//进站压力，MPa
        setCenterStationGoP(control.ReadDouble(AllData1.CenterStationSet, "OutgoingP"));//出站压力，MPa
        setCenterStationComeT(control.ReadDouble(AllData1.CenterStationSet, "InletT"));//进站温度，℃
        setCenterStationGoT(control.ReadDouble(AllData1.CenterStationSet, "OutgoingT"));//出站温度，℃
        /**
         * 增压设备数据
         */
        setEquipmentTime(control.ReadString(AllData1.EquipmentSet, "Time"));//时间
        setEquipmentQ(control.ReadDouble(AllData1.EquipmentSet, "Q"));//流量，m3/d
        setEquipmentComeP(control.ReadDouble(AllData1.EquipmentSet, "InletP"));//进口压力，MPa
        setEquipmentGoP(control.ReadDouble(AllData1.EquipmentSet, "OutgoingP"));//出口压力，MPa
        setEquipmentComeT(control.ReadDouble(AllData1.EquipmentSet, "InletT"));//进口温度，℃
        setEquipmentGoT(control.ReadDouble(AllData1.EquipmentSet, "OutgoingT"));//出口温度，℃
        setEquipmentI(control.ReadDouble(AllData1.EquipmentSet, "I"));//电流，A
        setEquipmentV(control.ReadDouble(AllData1.EquipmentSet, "V"));//电压,V
        setEquipmentTruePower(control.ReadDouble(AllData1.EquipmentSet, "ActualPower"));//真实功率，KW
        setEquipmentCondition(control.ReadString(AllData1.EquipmentSet, "Condition"));//运转状况
        /**
         * 管道数据
         */
        setPipeTime(control.ReadString(AllData1.PipeSet, "Time"));//时间
        setPipeQ(control.ReadDouble(AllData1.PipeSet, "ActualQ"));//实际Q，m3/d
        setPipeTo(control.ReadDouble(AllData1.PipeSet, "SurroundT"));//环境温度，℃
        PipeCL = control.ReadDouble(AllData1.PipeSet, "Pipe_CL");//管道优化常数
        Real_PipeCL = control.ReadDouble(AllData1.PipeSet, "Real_Pipe_CL");//管道优化常数
        Real_trans_rate = control.ReadDouble(AllData1.PipeSet, "TransportEfficiency");//管道优化常数
        Opti_trans_rate = control.ReadDouble(AllData1.PipeSet, "Opti_Efficiency");//管道优化常数
        Opti_PipeQ = control.ReadDouble(AllData1.PipeSet, "Opti_Q");//管道优化常数
        /**
         * 地层数据
         */
        CoalSeamTime = control.ReadString(AllData1.CoalSeamSet, "Time");//Time
        CoalNowPorosity = control.ReadDouble(AllData1.CoalSeamSet, "NowPorosity");//地层孔隙度
        CoalNowPermeability = control.ReadDouble(AllData1.CoalSeamSet, "NowPermeability");//地层目前渗透率
        CumulativeWater = control.ReadDouble(AllData1.CoalSeamSet, "CumulativeWaterQ");//累计产水量，m3
        CumulativeGas = control.ReadDouble(AllData1.CoalSeamSet, "CumulativeGasQ");//累计产气量，m3
        NowP = control.ReadDouble(AllData1.CoalSeamSet, "NowP");//目前地层压力，MPa

//        System.out.println("DynamicData输入完毕，举例：Pipe_CL=" + PipeCL);

    }

    private Map<String, Double> map(List<String> name, List<Double> value) {
        if (name.size() != value.size()) {
            System.out.println("DynamicData3.map():输入名与数据个数不等！");
        }
        Map<String, Double> map = new HashMap();
        int num = name.size();
        for (int i = 0; i < num; i++) {
            map.put(name.get(i), value.get(i));
        }
        return map;
    }

    /**
     * @return the WellTime
     */
    public List<String> getWellTime() {
        return WellTime;
    }

    /**
     * @param aWellTime the WellTime to set
     */
    public void setWellTime(List<String> aWellTime) {
        WellTime = aWellTime;
    }

    /**
     * @return the WellQ
     */
    public List<Double> getWellQ() {
        return WellQ;
    }

    /**
     * @param aWellQ the WellQ to set
     */
    public void setWellQ(List<Double> aWellQ) {
        WellQ = aWellQ;
    }

    /**
     * @return the WellTP
     */
    public List<Double> getWellTP() {
        return WellTP;
    }

    /**
     * @param aWellTP the WellTP to set
     */
    public void setWellTP(List<Double> aWellTP) {
        WellTP = aWellTP;
    }

    /**
     * @return the WellHP
     */
    public List<Double> getWellHP() {
        return WellHP;
    }

    /**
     * @param aWellHP the WellHP to set
     */
    public void setWellHP(List<Double> aWellHP) {
        WellHP = aWellHP;
    }

    /**
     * @return the Well_Pwf
     */
    public List<Double> getWellLP() {
        return Well_Pwf;
    }

    /**
     * @param aWellLP the Well_Pwf to set
     */
    public void setWellLP(List<Double> aWellLP) {
        Well_Pwf = aWellLP;
    }

    /**
     * @return the WellGasT
     */
    public List<Double> getWellT() {
        return WellGasT;
    }

    /**
     * @param aWellT the WellGasT to set
     */
    public void setWellT(List<Double> aWellT) {
        WellGasT = aWellT;
    }

    /**
     * @return the WellzuiD
     */
    public List<Double> getWellzuiD() {
        return WellzuiD;
    }

    /**
     * @param aWellzuiD the WellzuiD to set
     */
    public void setWellzuiD(List<Double> aWellzuiD) {
        WellzuiD = aWellzuiD;
    }

    /**
     * @return the ValveTime
     */
    public List<String> getValveTime() {
        return ValveTime;
    }

    /**
     * @param aValveTime the ValveTime to set
     */
    public void setValveTime(List<String> aValveTime) {
        ValveTime = aValveTime;
    }

    /**
     * @return the ValveQ
     */
    public List<Double> getValveQ() {
        return ValveQ;
    }

    /**
     * @param aValveQ the ValveQ to set
     */
    public void setValveQ(List<Double> aValveQ) {
        ValveQ = aValveQ;
    }

    /**
     * @return the ValveP
     */
    public List<Double> getValveP() {
        return ValveP;
    }

    /**
     * @param aValveP the ValveP to set
     */
    public void setValveP(List<Double> aValveP) {
        ValveP = aValveP;
    }

    /**
     * @return the ValveT
     */
    public List<Double> getValveT() {
        return ValveT;
    }

    /**
     * @param aValveT the ValveT to set
     */
    public void setValveT(List<Double> aValveT) {
        ValveT = aValveT;
    }

    /**
     * @return the StationTime
     */
    public List<String> getStationTime() {
        return StationTime;
    }

    /**
     * @param aStationTime the StationTime to set
     */
    public void setStationTime(List<String> aStationTime) {
        StationTime = aStationTime;
    }

    /**
     * @return the StationQ
     */
    public List<Double> getStationQ() {
        return StationQ;
    }

    /**
     * @param aStationQ the StationQ to set
     */
    public void setStationQ(List<Double> aStationQ) {
        StationQ = aStationQ;
    }

    /**
     * @return the StationComeP
     */
    public List<Double> getStationComeP() {
        return StationComeP;
    }

    /**
     * @param aStationComeP the StationComeP to set
     */
    public void setStationComeP(List<Double> aStationComeP) {
        StationComeP = aStationComeP;
    }

    /**
     * @return the StationGoP
     */
    public List<Double> getStationGoP() {
        return StationGoP;
    }

    /**
     * @param aStationGoP the StationGoP to set
     */
    public void setStationGoP(List<Double> aStationGoP) {
        StationGoP = aStationGoP;
    }

    /**
     * @return the StationComeT
     */
    public List<Double> getStationComeT() {
        return StationComeT;
    }

    /**
     * @param aStationComeT the StationComeT to set
     */
    public void setStationComeT(List<Double> aStationComeT) {
        StationComeT = aStationComeT;
    }

    /**
     * @return the StationGoT
     */
    public List<Double> getStationGoT() {
        return StationGoT;
    }

    /**
     * @param aStationGoT the StationGoT to set
     */
    public void setStationGoT(List<Double> aStationGoT) {
        StationGoT = aStationGoT;
    }

    /**
     * @return the CenterStationTime
     */
    public List<String> getCenterStationTime() {
        return CenterStationTime;
    }

    /**
     * @param aCenterStationTime the CenterStationTime to set
     */
    public void setCenterStationTime(List<String> aCenterStationTime) {
        CenterStationTime = aCenterStationTime;
    }

    /**
     * @return the CenterStationQ
     */
    public List<Double> getCenterStationQ() {
        return CenterStationQ;
    }

    /**
     * @param aCenterStationQ the CenterStationQ to set
     */
    public void setCenterStationQ(List<Double> aCenterStationQ) {
        CenterStationQ = aCenterStationQ;
    }

    /**
     * @return the CenterStationComeP
     */
    public List<Double> getCenterStationComeP() {
        return CenterStationComeP;
    }

    /**
     * @param aCenterStationComeP the CenterStationComeP to set
     */
    public void setCenterStationComeP(List<Double> aCenterStationComeP) {
        CenterStationComeP = aCenterStationComeP;
    }

    /**
     * @return the CenterStationGoP
     */
    public List<Double> getCenterStationGoP() {
        return CenterStationGoP;
    }

    /**
     * @param aCenterStationGoP the CenterStationGoP to set
     */
    public void setCenterStationGoP(List<Double> aCenterStationGoP) {
        CenterStationGoP = aCenterStationGoP;
    }

    /**
     * @return the CenterStationComeT
     */
    public List<Double> getCenterStationComeT() {
        return CenterStationComeT;
    }

    /**
     * @param aCenterStationComeT the CenterStationComeT to set
     */
    public void setCenterStationComeT(List<Double> aCenterStationComeT) {
        CenterStationComeT = aCenterStationComeT;
    }

    /**
     * @return the CenterStationGoT
     */
    public List<Double> getCenterStationGoT() {
        return CenterStationGoT;
    }

    /**
     * @param aCenterStationGoT the CenterStationGoT to set
     */
    public void setCenterStationGoT(List<Double> aCenterStationGoT) {
        CenterStationGoT = aCenterStationGoT;
    }

    /**
     * @return the EquipmentTime
     */
    public List<String> getEquipmentTime() {
        return EquipmentTime;
    }

    /**
     * @param aEquipmentTime the EquipmentTime to set
     */
    public void setEquipmentTime(List<String> aEquipmentTime) {
        EquipmentTime = aEquipmentTime;
    }

    /**
     * @return the EquipmentQ
     */
    public List<Double> getEquipmentQ() {
        return EquipmentQ;
    }

    /**
     * @param aEquipmentQ the EquipmentQ to set
     */
    public void setEquipmentQ(List<Double> aEquipmentQ) {
        EquipmentQ = aEquipmentQ;
    }

    /**
     * @return the EquipmentComeP
     */
    public List<Double> getEquipmentComeP() {
        return EquipmentComeP;
    }

    /**
     * @param aEquipmentComeP the EquipmentComeP to set
     */
    public void setEquipmentComeP(List<Double> aEquipmentComeP) {
        EquipmentComeP = aEquipmentComeP;
    }

    /**
     * @return the EquipmentGoP
     */
    public List<Double> getEquipmentGoP() {
        return EquipmentGoP;
    }

    /**
     * @param aEquipmentGoP the EquipmentGoP to set
     */
    public void setEquipmentGoP(List<Double> aEquipmentGoP) {
        EquipmentGoP = aEquipmentGoP;
    }

    /**
     * @return the EquipmentComeT
     */
    public List<Double> getEquipmentComeT() {
        return EquipmentComeT;
    }

    /**
     * @param aEquipmentComeT the EquipmentComeT to set
     */
    public void setEquipmentComeT(List<Double> aEquipmentComeT) {
        EquipmentComeT = aEquipmentComeT;
    }

    /**
     * @return the EquipmentGoT
     */
    public List<Double> getEquipmentGoT() {
        return EquipmentGoT;
    }

    /**
     * @param aEquipmentGoT the EquipmentGoT to set
     */
    public void setEquipmentGoT(List<Double> aEquipmentGoT) {
        EquipmentGoT = aEquipmentGoT;
    }

    /**
     * @return the EquipmentI
     */
    public List<Double> getEquipmentI() {
        return EquipmentI;
    }

    /**
     * @param aEquipmentI the EquipmentI to set
     */
    public void setEquipmentI(List<Double> aEquipmentI) {
        EquipmentI = aEquipmentI;
    }

    /**
     * @return the EquipmentV
     */
    public List<Double> getEquipmentV() {
        return EquipmentV;
    }

    /**
     * @param aEquipmentV the EquipmentV to set
     */
    public void setEquipmentV(List<Double> aEquipmentV) {
        EquipmentV = aEquipmentV;
    }

    /**
     * @return the EquipmentTruePower
     */
    public List<Double> getEquipmentTruePower() {
        return EquipmentTruePower;
    }

    /**
     * @param aEquipmentTruePower the EquipmentTruePower to set
     */
    public void setEquipmentTruePower(List<Double> aEquipmentTruePower) {
        EquipmentTruePower = aEquipmentTruePower;
    }

    /**
     * @return the EquipmentCondition
     */
    public List<String> getEquipmentCondition() {
        return EquipmentCondition;
    }

    /**
     * @param aEquipmentCondition the EquipmentCondition to set
     */
    public void setEquipmentCondition(List<String> aEquipmentCondition) {
        EquipmentCondition = aEquipmentCondition;
    }

    /**
     * @return the PipeTime
     */
    public List<String> getPipeTime() {
        return PipeTime;
    }

    /**
     * @param aPipeTime the PipeTime to set
     */
    public void setPipeTime(List<String> aPipeTime) {
        PipeTime = aPipeTime;
    }

    /**
     * @return the PipeQ
     */
    public List<Double> getPipeQ() {
        return PipeQ;
    }

    /**
     * @param aPipeQ the PipeQ to set
     */
    public void setPipeQ(List<Double> aPipeQ) {
        PipeQ = aPipeQ;
    }

    /**
     * @return the PipeTo
     */
    public List<Double> getPipeTo() {
        return PipeTo;
    }

    /**
     * @param aPipeTo the PipeTo to set
     */
    public void setPipeTo(List<Double> aPipeTo) {
        PipeTo = aPipeTo;
    }

}
