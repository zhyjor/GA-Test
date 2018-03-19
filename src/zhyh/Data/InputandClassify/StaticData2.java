/*
 *本类是将ALLData中读入的数据表进行分类存储的类
 */
package zhyh.Data.InputandClassify;

import zhyh.Tool.Data_resource.DBcontroller;
import java.util.List;

/*
 * 全部静态数据读入, 将AllData中的数据按照列转为List存储，主要存储序号、名称、座标等等不会改变的信息；
 * DynamicData存储生产数据等等不断变化的数据
 * 运行次序：2
 * @author 武浩
 */
public class StaticData2 {

    /**
     * 井数据
     */
    private static List<Integer> WellNo;//井序号
    public static List<String> WellBelong;//井所属区块
    private static List<String> WellName;//井名称
    private static List<String> Formation;//产气地层
    private static List<Double> EquaA;//产能二项式系数A
    private static List<Double> EquaB;//产能二项式系数B
    public static List<Double> Pr;//现在地层压力，MPa
    public static List<Double> PFDeep;//产层深度,m
    public static List<Double> PFThickness;//产层厚度,m
    public static List<Double> CasingD;//套管直径,mm
    public static List<Double> TubingD;//油管直径，mm
    public static List<Double> Pwf_max;//优化得井底流压最大值，MPa
    public static List<Double> Pwf_min;//优化得井底流压最小值，MPa
    public static List<Double> Q_max;//优化得井底流量最大值，m3/d
    public static List<Double> Q_min;//优化得井底流量最小值，m3/d
    public static List<Double> Q_best;//优化得最佳产量，m3/d
    public static List<Double> Well_matching;
    public static List<Double> Well_Pipe_matching;

    /**
     * 阀组数据
     */
    private static List<Integer> ValveNo;//阀组序号
    private static List<String> ValveBelong;//所属区块
    private static List<String> ValveName;//名称

    /**
     * 集气站数据,
     */
    private static List<Integer> StationNo;//序号
    private static List<String> StationBelong;//所属区块
    private static List<String> StationName;//名称

    /**
     * 中央处理厂数据
     */
    private static List<Integer> CenterStationNo;//序号
    private static List<String> CenterStationBelong;//所属区块
    private static List<String> CenterStationName;//名称

    /**
     * 增压设备数据
     */
    private static List<Integer> EquipmentNo;//序号
    private static List<String> EquipmentBelong;//所属区块
    private static List<String> EquipmentName;//名称
    private static List<Double> EquipmentRatedPower;//设备额定功率
    private static List<Double> EquipmentRatedSpeed;//设备额定转速

    /**
     * 管道数据
     */
    private static List<Integer> PipeNo;//序号
    private static List<Double> PipeLength;//管道长度,m
    private static List<Double> PipeD;//管道直径,mm
    private static List<Double> PipeThick;//壁厚,mm
    public static List<Double> PipeIdealQ;//理论流量,m3/d
    private static List<String> PipeStartPoint;//起点
    private static List<String> PipeEndPoint;//终点
    private static List<Double> Ke;//管道粗糙度,mm
    public static List<Double> Mozu;//管道摩阻系数
    public static List<Double> Economical_Speed;//管道经济流速,m/s

    /**
     * 煤层数据
     */
    private static List<Integer> CoalSeamNo;//序号
    private static List<String> CoalSeamName;//名称
    private static List<Double> IFP;//原始地层压力,MPa
    private static List<Double> CoalSeamA;//地层面积,Km2
    private static List<Double> Posong;//泊松比
    private static List<Double> Xd;//峰后强度退化指数
    private static List<Double> SigmaC;//单轴实验下的抗压强度,MPa
    private static List<Double> SigmaCr;//峰后残余强度,MPa
    private static List<Double> C;//岩石内聚力,MPa
    private static List<Double> Cr;//残余岩石内聚力,MPa
    private static List<Double> Fai;//岩石内摩擦角，°
    private static List<Double> Fair;//岩石残余内摩擦角，°
    private static List<Double> St;//岩石抗拉强度，MPa
    private static List<Double> VolModulus;//体积弹性模量
    private static List<Double> IniPorosity;//原始孔隙度，%
    private static List<Double> IniPermeability;//原始渗透率,mD
    private static List<Double> CoalSeamT;//煤层温度，℃
    private static List<Double> CoalSeamDensity;//煤层密度，kg/m3
    public static List<Double> deltaBcu;//单轴时的强度退化量，MPa
    public static List<Double> md;//常数

    private static AllData1 d = new AllData1();
    private static DBcontroller control = new DBcontroller();

    /**
     * @return the Fair
     */
    public static List<Double> getFair() {
        return Fair;
    }

    /**
     * @return the Cr
     */
    public static List<Double> getCr() {
        return Cr;
    }

    public void updater() {//更新上面的数据

        /*
         * 井数据
         */
        setWellNo(control.ReadInt(AllData1.WellSet, "SequenceNumber"));//序号
        setWellBelong(control.ReadString(AllData1.WellSet, "Region"));//所属区块
        setWellName(control.ReadString(AllData1.WellSet, "Name"));//名称
        Formation = control.ReadString(AllData1.WellSet, "StratigraphicName");//产气地层
        EquaA = control.ReadDouble(AllData1.WellSet, "ProductivityEquaA");//产能二项式系数A
        EquaB = control.ReadDouble(AllData1.WellSet, "ProductivityEquaB");//产能二项式系数B
        PFDeep = control.ReadDouble(AllData1.WellSet, "FormationDepth");//产层深度
        PFThickness = control.ReadDouble(AllData1.WellSet, "PFThickness");//产层厚度
        Pr = control.ReadDouble(AllData1.WellSet, "Pr");//产层厚度
        CasingD = control.ReadDouble(AllData1.WellSet, "CasingD");//套管直径
        TubingD = control.ReadDouble(AllData1.WellSet, "TubingD");//油管直径
        Pwf_max = control.ReadDouble(AllData1.WellSet, "Pwf_max");//优化得井底流压最大值，MPa
        Pwf_min = control.ReadDouble(AllData1.WellSet, "Pwf_min");//优化得井底流压最小值，MPa
        Q_max = control.ReadDouble(AllData1.WellSet, "Q_max");//优化得井底流量最大值，m3/d
        Q_min = control.ReadDouble(AllData1.WellSet, "Q_min");//优化得井底流量最小值，m3/d
        Q_best = control.ReadDouble(AllData1.WellSet, "Best_Q");//优化得井底流量最佳值，m3/d
        Well_matching = control.ReadDouble(AllData1.WellSet, "Well_Matching");//优化得井间匹配性
        Well_Pipe_matching = control.ReadDouble(AllData1.WellSet, "Well_Pipenet_Matching");//优化得井管匹配性

        /**
         * 阀组数据
         */
        setValveNo(control.ReadInt(AllData1.ValveSet, "SequenceNumber"));//阀组序号
        setValveBelong(control.ReadString(AllData1.ValveSet, "Region"));//所属区块
        setValveName(control.ReadString(AllData1.ValveSet, "Name"));//名称

        /**
         * 集气站数据
         */
        setStationNo(control.ReadInt(AllData1.StationSet, "SequenceNumber"));//序号
        setStationBelong(control.ReadString(AllData1.StationSet, "Region"));//所属区块
        setStationName(control.ReadString(AllData1.StationSet, "Name"));//名称

        /**
         * 中央处理厂数据
         */
        setCenterStationNo(control.ReadInt(AllData1.CenterStationSet, "SequenceNumber"));//序号
        setCenterStationBelong(control.ReadString(AllData1.CenterStationSet, "Region"));//所属区块
        setCenterStationName(control.ReadString(AllData1.CenterStationSet, "Name"));//名称

        /**
         * 增压设备数据
         */
        setEquipmentNo(control.ReadInt(AllData1.EquipmentSet, "SequenceNumber"));//序号
        setEquipmentBelong(control.ReadString(AllData1.EquipmentSet, "Region"));//所属区块
        setEquipmentName(control.ReadString(AllData1.EquipmentSet, "Name"));//名称
        setEquipmentRatedPower(control.ReadDouble(AllData1.EquipmentSet, "RatedPower"));//设备额定功率
        setEquipmentRatedSpeed(control.ReadDouble(AllData1.EquipmentSet, "RatedSpeed"));//设备额定转速

        /**
         * 管道数据
         */
        setPipeNo(control.ReadInt(AllData1.PipeSet, "SequenceNumber"));//序号
        setPipeLength(control.ReadDouble(AllData1.PipeSet, "Length"));//管道长度
        setPipeD(control.ReadDouble(AllData1.PipeSet, "D"));//管道直径
        setPipeThick(control.ReadDouble(AllData1.PipeSet, "Thickness"));//壁厚
        setPipeIdealQ(control.ReadDouble(AllData1.PipeSet, "TheoreticalQ"));//理论流量
        setPipeStartPoint(control.ReadString(AllData1.PipeSet, "StartPointName"));//起点
        setPipeEndPoint(control.ReadString(AllData1.PipeSet, "EndPointName"));//终点
        Ke = control.ReadDouble(AllData1.PipeSet, "Ke");
        Mozu = control.ReadDouble(AllData1.PipeSet, "mozu_para");
        Economical_Speed = control.ReadDouble(AllData1.PipeSet, "Economical_Speed");

        /**
         * 地层数据
         */
        CoalSeamNo = control.ReadInt(AllData1.CoalSeamSet, "SequenceNumber");//序号
        CoalSeamName = control.ReadString(AllData1.CoalSeamSet, "Name");//名称
        IFP = control.ReadDouble(AllData1.CoalSeamSet, "IFP");//原始地层压力，MPa
        CoalSeamA = control.ReadDouble(AllData1.CoalSeamSet, "CoalSeamA");//地层面积，km2
        Posong = control.ReadDouble(AllData1.CoalSeamSet, "Posong");//泊松比
        Xd = control.ReadDouble(AllData1.CoalSeamSet, "Xd");//峰后强度退化指数
        SigmaC = control.ReadDouble(AllData1.CoalSeamSet, "UCS");//单轴实验下的抗压强度，MPa
        SigmaCr = control.ReadDouble(AllData1.CoalSeamSet, "sigmaUCS");//峰后残余强度，MPa
        C = control.ReadDouble(AllData1.CoalSeamSet, "Cohesion");//岩石粘聚力，MPa
        Cr = control.ReadDouble(AllData1.CoalSeamSet, "Cohesion_R");//残余岩石粘聚力，MPa
        Fai = control.ReadDouble(AllData1.CoalSeamSet, "fai");//岩石内摩擦角，°
        Fair = control.ReadDouble(AllData1.CoalSeamSet, "fair");//残余岩石内摩擦角，°
        St = control.ReadDouble(AllData1.CoalSeamSet, "St");//岩石抗拉强度，MPa
        VolModulus = control.ReadDouble(AllData1.CoalSeamSet, "VolumeModulus");//体积弹性模量
        IniPorosity = control.ReadDouble(AllData1.CoalSeamSet, "InitialPorosity");//原始孔隙度
        IniPermeability = control.ReadDouble(AllData1.CoalSeamSet, "InitialPermeability");//原始渗透率
        CoalSeamT = control.ReadDouble(AllData1.CoalSeamSet, "T");//煤层温度，℃
        CoalSeamDensity = control.ReadDouble(AllData1.CoalSeamSet, "CoalSeamDensity");//煤层密度，km/m3
        deltaBcu = control.ReadDouble(AllData1.CoalSeamSet, "deltaBcu");//单轴强度退化量，MPa
        md = control.ReadDouble(AllData1.CoalSeamSet, "md");
//
//        System.out.println("StaticData准备完毕！！" + PipeStartPoint);
//        System.out.println("StaticData准备完毕！！" + PipeEndPoint);
    }

    /**
     * @return the WellNo
     */
    public static List<Integer> getWellNo() {
        return WellNo;
    }

    /**
     * @param aWellNo the WellNo to set
     */
    public static void setWellNo(List<Integer> aWellNo) {
        WellNo = aWellNo;
    }

    /**
     * @return the WellBelong
     */
    public static List<String> getWellBelong() {
        return WellBelong;
    }

    /**
     * @param aWellBelong the WellBelong to set
     */
    public static void setWellBelong(List<String> aWellBelong) {
        WellBelong = aWellBelong;
    }

    /**
     * @return the WellName
     */
    public static List<String> getWellName() {
        return WellName;
    }

    /**
     * @param aWellName the WellName to set
     */
    public static void setWellName(List<String> aWellName) {
        WellName = aWellName;
    }

    /**
     * @return the Formation
     */
    public static List<String> getFormation() {
        return Formation;
    }

    /**
     * @param aFormation the Formation to set
     */
    public static void setFormation(List<String> aFormation) {
        Formation = aFormation;
    }

    /**
     * @return the EquaA
     */
    public static List<Double> getEquaA() {
        return EquaA;
    }

    /**
     * @param aEquaA the EquaA to set
     */
    public static void setEquaA(List<Double> aEquaA) {
        EquaA = aEquaA;
    }

    /**
     * @return the EquaB
     */
    public static List<Double> getEquaB() {
        return EquaB;
    }

    /**
     * @param aEquaB the EquaB to set
     */
    public static void setEquaB(List<Double> aEquaB) {
        EquaB = aEquaB;
    }

    /**
     * @return the ValveNo
     */
    public static List<Integer> getValveNo() {
        return ValveNo;
    }

    /**
     * @param aValveNo the ValveNo to set
     */
    public static void setValveNo(List<Integer> aValveNo) {
        ValveNo = aValveNo;
    }

    /**
     * @return the ValveBelong
     */
    public static List<String> getValveBelong() {
        return ValveBelong;
    }

    /**
     * @param aValveBelong the ValveBelong to set
     */
    public static void setValveBelong(List<String> aValveBelong) {
        ValveBelong = aValveBelong;
    }

    /**
     * @return the ValveName
     */
    public static List<String> getValveName() {
        return ValveName;
    }

    /**
     * @param aValveName the ValveName to set
     */
    public static void setValveName(List<String> aValveName) {
        ValveName = aValveName;
    }

    /**
     * @return the StationNo
     */
    public static List<Integer> getStationNo() {
        return StationNo;
    }

    /**
     * @param aStationNo the StationNo to set
     */
    public static void setStationNo(List<Integer> aStationNo) {
        StationNo = aStationNo;
    }

    /**
     * @return the StationBelong
     */
    public static List<String> getStationBelong() {
        return StationBelong;
    }

    /**
     * @param aStationBelong the StationBelong to set
     */
    public static void setStationBelong(List<String> aStationBelong) {
        StationBelong = aStationBelong;
    }

    /**
     * @return the StationName
     */
    public static List<String> getStationName() {
        return StationName;
    }

    /**
     * @param aStationName the StationName to set
     */
    public static void setStationName(List<String> aStationName) {
        StationName = aStationName;
    }

    /**
     * @return the CenterStationNo
     */
    public static List<Integer> getCenterStationNo() {
        return CenterStationNo;
    }

    /**
     * @param aCenterStationNo the CenterStationNo to set
     */
    public static void setCenterStationNo(List<Integer> aCenterStationNo) {
        CenterStationNo = aCenterStationNo;
    }

    /**
     * @return the CenterStationBelong
     */
    public static List<String> getCenterStationBelong() {
        return CenterStationBelong;
    }

    /**
     * @param aCenterStationBelong the CenterStationBelong to set
     */
    public static void setCenterStationBelong(List<String> aCenterStationBelong) {
        CenterStationBelong = aCenterStationBelong;
    }

    /**
     * @return the CenterStationName
     */
    public static List<String> getCenterStationName() {
        return CenterStationName;
    }

    /**
     * @param aCenterStationName the CenterStationName to set
     */
    public static void setCenterStationName(List<String> aCenterStationName) {
        CenterStationName = aCenterStationName;
    }

    /**
     * @return the EquipmentNo
     */
    public static List<Integer> getEquipmentNo() {
        return EquipmentNo;
    }

    /**
     * @param aEquipmentNo the EquipmentNo to set
     */
    public static void setEquipmentNo(List<Integer> aEquipmentNo) {
        EquipmentNo = aEquipmentNo;
    }

    /**
     * @return the EquipmentBelong
     */
    public static List<String> getEquipmentBelong() {
        return EquipmentBelong;
    }

    /**
     * @param aEquipmentBelong the EquipmentBelong to set
     */
    public static void setEquipmentBelong(List<String> aEquipmentBelong) {
        EquipmentBelong = aEquipmentBelong;
    }

    /**
     * @return the EquipmentName
     */
    public static List<String> getEquipmentName() {
        return EquipmentName;
    }

    /**
     * @param aEquipmentName the EquipmentName to set
     */
    public static void setEquipmentName(List<String> aEquipmentName) {
        EquipmentName = aEquipmentName;
    }

    /**
     * @return the EquipmentRatedPower
     */
    public static List<Double> getEquipmentRatedPower() {
        return EquipmentRatedPower;
    }

    /**
     * @param aEquipmentRatedPower the EquipmentRatedPower to set
     */
    public static void setEquipmentRatedPower(List<Double> aEquipmentRatedPower) {
        EquipmentRatedPower = aEquipmentRatedPower;
    }

    /**
     * @return the EquipmentRatedSpeed
     */
    public static List<Double> getEquipmentRatedSpeed() {
        return EquipmentRatedSpeed;
    }

    /**
     * @param aEquipmentRatedSpeed the EquipmentRatedSpeed to set
     */
    public static void setEquipmentRatedSpeed(List<Double> aEquipmentRatedSpeed) {
        EquipmentRatedSpeed = aEquipmentRatedSpeed;
    }

    /**
     * @return the PipeNo
     */
    public static List<Integer> getPipeNo() {
        return PipeNo;
    }

    /**
     * @param aPipeNo the PipeNo to set
     */
    public static void setPipeNo(List<Integer> aPipeNo) {
        PipeNo = aPipeNo;
    }

    /**
     * @return the PipeLength
     */
    public static List<Double> getPipeLength() {
        return PipeLength;
    }

    /**
     * @param aPipeLength the PipeLength to set
     */
    public static void setPipeLength(List<Double> aPipeLength) {
        PipeLength = aPipeLength;
    }

    /**
     * @return the PipeD
     */
    public static List<Double> getPipeD() {
        return PipeD;
    }

    /**
     * @param aPipeD the PipeD to set
     */
    public static void setPipeD(List<Double> aPipeD) {
        PipeD = aPipeD;
    }

    /**
     * @return the PipeThick
     */
    public static List<Double> getPipeThick() {
        return PipeThick;
    }

    /**
     * @param aPipeThick the PipeThick to set
     */
    public static void setPipeThick(List<Double> aPipeThick) {
        PipeThick = aPipeThick;
    }

    /**
     * @return the PipeIdealQ
     */
    public static List<Double> getPipeIdealQ() {
        return PipeIdealQ;
    }

    /**
     * @param aPipeIdealQ the PipeIdealQ to set
     */
    public static void setPipeIdealQ(List<Double> aPipeIdealQ) {
        PipeIdealQ = aPipeIdealQ;
    }

    /**
     * @return the PipeStartPoint
     */
    public static List<String> getPipeStartPoint() {
        return PipeStartPoint;
    }

    /**
     * @param aPipeStartPoint the PipeStartPoint to set
     */
    public static void setPipeStartPoint(List<String> aPipeStartPoint) {
        PipeStartPoint = aPipeStartPoint;
    }

    /**
     * @return the PipeEndPoint
     */
    public static List<String> getPipeEndPoint() {
        return PipeEndPoint;
    }

    /**
     * @param aPipeEndPoint the PipeEndPoint to set
     */
    public static void setPipeEndPoint(List<String> aPipeEndPoint) {
        PipeEndPoint = aPipeEndPoint;
    }

    /**
     * @return the Ke
     */
    public static List<Double> getKe() {
        return Ke;
    }

    /**
     * @param aKe the Ke to set
     */
    public static void setKe(List<Double> aKe) {
        Ke = aKe;
    }

    /**
     * @return the CoalSeamNo
     */
    public static List<Integer> getCoalSeamNo() {
        return CoalSeamNo;
    }

    /**
     * @param aCoalSeamNo the CoalSeamNo to set
     */
    public static void setCoalSeamNo(List<Integer> aCoalSeamNo) {
        CoalSeamNo = aCoalSeamNo;
    }

    /**
     * @return the CoalSeamName
     */
    public static List<String> getCoalSeamName() {
        return CoalSeamName;
    }

    /**
     * @param aCoalSeamName the CoalSeamName to set
     */
    public static void setCoalSeamName(List<String> aCoalSeamName) {
        CoalSeamName = aCoalSeamName;
    }

    /**
     * @return the IFP
     */
    public static List<Double> getIFP() {
        return IFP;
    }

    /**
     * @param aIFP the IFP to set
     */
    public static void setIFP(List<Double> aIFP) {
        IFP = aIFP;
    }

    /**
     * @return the CoalSeamA
     */
    public static List<Double> getCoalSeamA() {
        return CoalSeamA;
    }

    /**
     * @param aCoalSeamA the CoalSeamA to set
     */
    public static void setCoalSeamA(List<Double> aCoalSeamA) {
        CoalSeamA = aCoalSeamA;
    }

    /**
     * @return the Posong
     */
    public static List<Double> getPosong() {
        return Posong;
    }

    /**
     * @param aPosong the Posong to set
     */
    public static void setPosong(List<Double> aPosong) {
        Posong = aPosong;
    }

    /**
     * @return the Xd
     */
    public static List<Double> getXd() {
        return Xd;
    }

    /**
     * @param aXd the Xd to set
     */
    public static void setXd(List<Double> aXd) {
        Xd = aXd;
    }

    /**
     * @return the SigmaC
     */
    public static List<Double> getSigmaC() {
        return SigmaC;
    }

    /**
     * @param aSigmaC the SigmaC to set
     */
    public static void setSigmaC(List<Double> aSigmaC) {
        SigmaC = aSigmaC;
    }

    /**
     * @return the SigmaCr
     */
    public static List<Double> getSigmaCr() {
        return SigmaCr;
    }

    /**
     * @param aSigmaCr the SigmaCr to set
     */
    public static void setSigmaCr(List<Double> aSigmaCr) {
        SigmaCr = aSigmaCr;
    }

    /**
     * @return the C
     */
    public static List<Double> getC() {
        return C;
    }

    /**
     * @param aC the C to set
     */
    public static void setC(List<Double> aC) {
        C = aC;
    }

    /**
     * @return the Fai
     */
    public static List<Double> getFai() {
        return Fai;
    }

    /**
     * @param aFai the Fai to set
     */
    public static void setFai(List<Double> aFai) {
        Fai = aFai;
    }

    /**
     * @return the St
     */
    public static List<Double> getSt() {
        return St;
    }

    /**
     * @param aSt the St to set
     */
    public static void setSt(List<Double> aSt) {
        St = aSt;
    }

    /**
     * @return the VolModulus
     */
    public static List<Double> getVolModulus() {
        return VolModulus;
    }

    /**
     * @param aVolModulus the VolModulus to set
     */
    public static void setVolModulus(List<Double> aVolModulus) {
        VolModulus = aVolModulus;
    }

    /**
     * @return the IniPorosity
     */
    public static List<Double> getIniPorosity() {
        return IniPorosity;
    }

    /**
     * @param aIniPorosity the IniPorosity to set
     */
    public static void setIniPorosity(List<Double> aIniPorosity) {
        IniPorosity = aIniPorosity;
    }

    /**
     * @return the IniPermeability
     */
    public static List<Double> getIniPermeability() {
        return IniPermeability;
    }

    /**
     * @param aIniPermeability the IniPermeability to set
     */
    public static void setIniPermeability(List<Double> aIniPermeability) {
        IniPermeability = aIniPermeability;
    }

    /**
     * @return the CoalSeamT
     */
    public static List<Double> getCoalSeamT() {
        return CoalSeamT;
    }

    /**
     * @param aCoalSeamT the CoalSeamT to set
     */
    public static void setCoalSeamT(List<Double> aCoalSeamT) {
        CoalSeamT = aCoalSeamT;
    }

    /**
     * @return the CoalSeamDensity
     */
    public static List<Double> getCoalSeamDensity() {
        return CoalSeamDensity;
    }

    /**
     * @param aCoalSeamDensity the CoalSeamDensity to set
     */
    public static void setCoalSeamDensity(List<Double> aCoalSeamDensity) {
        CoalSeamDensity = aCoalSeamDensity;
    }

    /**
     * @return the d
     */
    public static AllData1 getD() {
        return d;
    }

    /**
     * @param aD the d to set
     */
    public static void setD(AllData1 aD) {
        d = aD;
    }

    /**
     * @return the control
     */
    public static DBcontroller getControl() {
        return control;
    }

    /**
     * @param aControl the control to set
     */
    public static void setControl(DBcontroller aControl) {
        control = aControl;
    }

}
