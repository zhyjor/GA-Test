/*
 * 本包只存储CreatTree与DataManager中产生的必要数据，包括生产数据、树结构、连接关系等，均已Map的形式存储，方便后面的模型调用
 */
package zhyh.Data.MapStorage;

import zhyh.Data.CreateTree.BuildTree5;
import zhyh.Data.InputandClassify.NameList4;
import zhyh.Data.CreateTree.ChildrenMap6;
import zhyh.Tool.Data_resource.DBcontroller;
import zhyh.Data.InputandClassify.StaticData2;
import zhyh.Data.CreateTree.ChildrenTree_NeedOptim;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于方便静态数据的集中调取，通过Run.Runkey()来初始化所有的数据。 管道数据的搜索key值均为：“起点名称+终点名称”
 * 检查管道起点终点是否正确，不正确就对调 更新管道数据
 *
 * @author 武浩
 */
public class StaticDataMap7 {

    private BuildTree5 bd;
    private ChildrenMap6 cd;

    /**
     * 树结构
     */
    public static Map<String, List<String>> Comemap;//各点的来流方向
    public static Map<String, String> Gomap;//各点的流出方向
    public static Map<String, Integer> Deepmap;//树结构中各点的深度
    public static Map<String, List<String>> Allbelongmap;//某个点下属全部子节点
    public static List<String> LeafNode;//树结构叶节点
    public static Map<String, Integer> ID;//点的类别，0为井，1为阀组，2为集气站，3为中央处理厂
    public static Map<String, List<String>> ArroundNodemap;
    public static List<String> rootofNeedOptiTree;//需要优化的子树的root点
    public static Map<String, List<String>> gotorootPath;//各个井到子树root点的路径
    public static Map<String, List<String>> allPoint_in_childtree;//需要优化的子树结构中全部点的名称，其值由 Starter3 类推送过来
    public static Map<String, List<String>> well_in_childtree;//需要优化的子树结构中全部井的名称，其值由 Starter3 类推送过来
    
    public static Map<String, List<String>> well_in_childtree_peican;//不同类型井的配产数据

    /**
     * 管道
     */
    public static List<String> PipeStartPointList;//管道起点名称，按照管道在表中的位置编号
    public static List<String> PipeEndPointList;//管道终点名称，按照管道在表中的位置编号
    public static Map<String, Integer> PipeNo;//管道的序号
    public static Map<String, Double> PipeDmap;//管内径,mm
    public static Map<String, Double> PipeLengthmap;//管长,m
    public static Map<String, Double> PipeKe;//管道粗糙度,mm
    public static Map<String, Double> PipeMozu;//管道摩阻系数
    public static Map<String, Double> Economical_Speed;//管道摩阻系数

    /**
     * 井
     */
    public static Map<String, String> WellFormatin;//气井产气地层
    public static Map<String, Double> WellCasingD;//套管直径，mm
    public static Map<String, Double> WellTubingD;//油管直径,mm
    public static Map<String, Double> EquaA;//产能二项式系数A
    public static Map<String, Double> EquaB;//产能二项式系数B
    public static Map<String, Double> PFDeepmap;//产层深度,m
    public static Map<String, Double> PFThicknessmap;//产层厚度，m
    public static Map<String, String> WellBelong;//井所属地
    public static Map<String, Double> Pr;//地层压力，MPa
    public static Map<String, Double> Pwf_max;//井底压力上限压力，MPa
    public static Map<String, Double> Pwf_min;//井底压力下限压力，MPa
    public static Map<String, Double> Q_max;//井产量上限，m3/d
    public static Map<String, Double> Q_min;//井产量下限，m3/d
    public static Map<String, Double> Q_best;//井最佳产量，m3/d
    public static Map<String, Double> Well_matching;//井间匹配性
    public static Map<String, Double> Well_pipe_matching;//井间匹配性
    
    public static Map<String, Double> Q_T_max;//井理论产量上限，m3/d
    public static Map<String, Double> Q_T_min;//井理论产量下限，m3/d

    /**
     * 地层
     */
    public static List<String> CoalSeamlist;//地层列表
    public static Map<String, Double> CoalIFP;//原始储层压力,MPa
    public static Map<String, Double> CoalSeamA;//储层面积,km2
    public static Map<String, Double> Posong;//岩石泊松比
    public static Map<String, Double> Xd;//峰后强度退化指数
    public static Map<String, Double> SigmaC;//单轴实验下的抗压强度,Pa
    public static Map<String, Double> SigmaCr;//峰后残余强度,Pa
    public static Map<String, Double> CoalC;//岩石粘聚力,Pa
    public static Map<String, Double> CoalCr;//残余岩石粘聚力,Pa
    public static Map<String, Double> CoalFai;//内摩擦角,°
    public static Map<String, Double> CoalFair;//残余内摩擦角,°
    public static Map<String, Double> CoalSt;//岩石张拉应力，MPa
    public static Map<String, Double> CoalVolModulus;//体积弹性模量
    public static Map<String, Double> CoalIniPorosity;//原始孔隙度 ,%
    public static Map<String, Double> CoalIniPermeability;//原始渗透率,mD
    public static Map<String, Double> CoalSeamT;//煤层温度,℃
    public static Map<String, Double> CoalSeamDensity;//煤层密度,kg/m3
    public static Map<String, Double> deltaBcumap;
    public static Map<String, Double> mdmap;//试验常数
    
    

    /**
     * 为上面变量赋值
     */
    public void init() {

        Comemap = new HashMap();//各点的来流方向
        Gomap = new HashMap();//各点的流出方向
        Deepmap = new HashMap();//树结构中各点的深度
        Allbelongmap = new HashMap();//某个点下属全部子节点
        LeafNode = new ArrayList();//树结构叶节点
        ID = new HashMap();//点的类别，0为井，1为阀组，2为集气站，3为中央处理厂
        ArroundNodemap = new HashMap();
        rootofNeedOptiTree = new ArrayList();//需要优化的子树的root点

        PipeStartPointList = new ArrayList();//管道起点名称，按照管道在表中的位置编号
        PipeEndPointList = new ArrayList();//管道终点名称，按照管道在表中的位置编号
        PipeNo = new HashMap();//管道的序号
        PipeDmap = new HashMap();//管内径,mm
        PipeLengthmap = new HashMap();//管长,m
        PipeKe = new HashMap();//管道粗糙度,mm
        PipeMozu = new HashMap();//管道摩阻系数
        Economical_Speed = new HashMap();//管道摩阻系数

        WellFormatin = new HashMap();//气井产气地层
        WellCasingD = new HashMap();//套管直径，mm
        WellTubingD = new HashMap();//油管直径,mm
        EquaA = new HashMap();//产能二项式系数A
        EquaB = new HashMap();//产能二项式系数B
        PFDeepmap = new HashMap();//产层深度,m
        PFThicknessmap = new HashMap();//产层厚度，m
        WellBelong = new HashMap();//井所属地
        Pr = new HashMap();//地层压力，MPa
        Pwf_max = new HashMap();//井底压力上限压力，MPa
        Pwf_min = new HashMap();//井底压力下限压力，MPa
        Q_max = new HashMap();//井产量上限，m3/d
        Q_min = new HashMap();//井产量下限，m3/d
        Q_best = new HashMap();//井最佳产量，m3/d
        Well_matching = new HashMap();//井间匹配性
        Well_pipe_matching = new HashMap();//井间匹配性
        
        Q_T_max = new HashMap();//井理论产量上限，m3/d
        Q_T_min = new HashMap();//井理论产量下限，m3/d

        CoalSeamlist = new ArrayList();//地层列表
        CoalIFP = new HashMap();//原始储层压力,MPa
        CoalSeamA = new HashMap();//储层面积,km2
        Posong = new HashMap();//岩石泊松比
        Xd = new HashMap();//峰后强度退化指数
        SigmaC = new HashMap();//单轴实验下的抗压强度,Pa
        SigmaCr = new HashMap();//峰后残余强度,Pa
        CoalC = new HashMap();//岩石粘聚力,Pa
        CoalCr = new HashMap();//残余岩石粘聚力,Pa
        CoalFai = new HashMap();//内摩擦角,°
        CoalFair = new HashMap();//残余内摩擦角,°
        CoalSt = new HashMap();//岩石张拉应力，MPa
        CoalVolModulus = new HashMap();//体积弹性模量
        CoalIniPorosity = new HashMap();//原始孔隙度 ,%
        CoalIniPermeability = new HashMap();//原始渗透率,mD
        CoalSeamT = new HashMap();//煤层温度,℃
        CoalSeamDensity = new HashMap();//煤层密度,kg/m3
        deltaBcumap = new HashMap();
        mdmap = new HashMap();//试验常数

        bd = new BuildTree5();
        cd = new ChildrenMap6();
        LeafNode = BuildTree5.LeafNode;
        Comemap = BuildTree5.Comemap;
        Gomap = bd.Gomap;
        Deepmap = bd.Depthmap;
        Allbelongmap = ChildrenMap6.Allbelongmap;
        ArroundNodemap = BuildTree5.arroundmap;
        rootofNeedOptiTree = ChildrenTree_NeedOptim.rootofNeedOptiTree;
        gotorootPath = ChildrenTree_NeedOptim.gotorootPath;

        ID = NameList4.ID;
        updateGuanduan();//判断更新管道表格中的起点终点(如果不对),并写入PipeStartlist、PipeEndPointList    Guandatamap();
        Guandatamap();
        WellSeamMap();
        formationMap();
//        System.out.println("");
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        System.out.println("StaticDataMap7更新完毕：");
    }

    private void Guandatamap() { //[长度，直径]（m，m）

        Map<String, Double> guand = new HashMap();
        Map<String, Double> guanl = new HashMap();
        PipeKe = new HashMap();
        List<String> list1 = PipeStartPointList;//管道端点1
        List<String> list2 = PipeEndPointList;//管道端点2
        List<Double> length = new ArrayList();//管长
        length = StaticData2.getPipeLength();
        List<Double> zhijing = new ArrayList();//管径
        zhijing = StaticData2.getPipeD();
        List<Double> bihou = new ArrayList();//壁厚
        bihou = StaticData2.getPipeThick();
        String qidian, zhongdian;
        String key;
        int num = list1.size();
        for (int i = 0; i < num; i++) {
            qidian = list1.get(i);
            zhongdian = list2.get(i);
            key = qidian + zhongdian;//以两个端点的名称作为key 
            guanl.put(key, length.get(i));//管长，m
            guand.put(key, (zhijing.get(i) - 2 * bihou.get(i)));//内径,m
            PipeKe.put(key, StaticData2.getKe().get(i));
            PipeMozu.put(key, StaticData2.Mozu.get(i));
            Economical_Speed.put(key, StaticData2.Economical_Speed.get(i));
        }
        PipeDmap = guand;
        PipeLengthmap = guanl;

    }

    /**
     * 将井的所属参数输入map
     */
    private void WellSeamMap() {

        List<String> Well = StaticData2.getWellName();
        int num = Well.size();
        String name;
        for (int i = 0; i < num; i++) {
            name = Well.get(i);
            WellFormatin.put(name, StaticData2.getFormation().get(i));//气井产气地层
            EquaA.put(name, StaticData2.getEquaA().get(i));//产能二项式系数A
            EquaB.put(name, StaticData2.getEquaB().get(i));//产能二项式系数B
            PFDeepmap.put(name, StaticData2.PFDeep.get(i));//产层深度
            PFThicknessmap.put(name, StaticData2.PFThickness.get(i));//产层厚度
            Pr.put(name, StaticData2.Pr.get(i));
            WellTubingD.put(name, StaticData2.TubingD.get(i));
            WellCasingD.put(name, StaticData2.CasingD.get(i));
            WellBelong.put(name, StaticData2.WellBelong.get(i));
            Pwf_max.put(name, StaticData2.Pwf_max.get(i));
            Pwf_min.put(name, StaticData2.Pwf_min.get(i));
            Q_max.put(name, StaticData2.Q_max.get(i));
            Q_min.put(name, StaticData2.Q_min.get(i));
            Q_best.put(name, StaticData2.Q_best.get(i));
            Well_matching.put(name, StaticData2.Well_matching.get(i));
            Well_pipe_matching.put(name, StaticData2.Well_Pipe_matching.get(i));
            
            Q_T_max.put(name, StaticData2.Q_T_max.get(i));
            Q_T_min.put(name, StaticData2.Q_T_min.get(i));

        }
    }

    /**
     * 地层参数的查询map
     */
    private void formationMap() {

        CoalSeamlist = StaticData2.getCoalSeamName();
        int num = CoalSeamlist.size();
        String name;
        for (int i = 0; i < num; i++) {
            name = CoalSeamlist.get(i);
            CoalIFP.put(name, StaticData2.getIFP().get(i));//原始储层压力,MPa
            CoalSeamA.put(name, StaticData2.getCoalSeamA().get(i));//储层面积,km2
            Posong.put(name, StaticData2.getPosong().get(i));//岩石泊松比
            Xd.put(name, StaticData2.getXd().get(i));//峰后强度退化指数
            SigmaC.put(name, StaticData2.getSigmaC().get(i));//单轴实验下的抗压强度,MPa
            SigmaCr.put(name, StaticData2.getSigmaCr().get(i));//峰后残余强度
            CoalC.put(name, StaticData2.getC().get(i));//岩石粘聚力，MPa
            CoalCr.put(name, StaticData2.getCr().get(i));//岩石粘聚力，MPa
            CoalFai.put(name, StaticData2.getFai().get(i));//内摩擦角
            CoalFair.put(name, StaticData2.getFair().get(i));//残余内摩擦角
            CoalSt.put(name, StaticData2.getSt().get(i));//岩石张拉应力
            CoalVolModulus.put(name, StaticData2.getVolModulus().get(i));//体积弹性模量
            CoalIniPorosity.put(name, StaticData2.getIniPorosity().get(i));//原始孔隙度 
            CoalIniPermeability.put(name, StaticData2.getIniPermeability().get(i));//原始渗透率
            CoalSeamT.put(name, StaticData2.getCoalSeamT().get(i));//煤层温度
            CoalSeamDensity.put(name, StaticData2.getCoalSeamDensity().get(i));//煤层密度,
            deltaBcumap.put(name, StaticData2.deltaBcu.get(i));//单轴时的强度退化量
            mdmap.put(name, StaticData2.md.get(i));//常数
        }
    }

    /**
     * 检查管道起点终点方向是否正确，若不对，调整位置,并将起点终点存为list
     */
    private void updateGuanduan() {
        PipeNo = new HashMap();
        DBcontroller control = new DBcontroller();
        List<String> list1 = StaticData2.getPipeStartPoint();//管道起点
        List<String> list2 = StaticData2.getPipeEndPoint();//管道终点
        PipeStartPointList = new ArrayList();
        PipeEndPointList = new ArrayList();
        Map<String, Integer> deep = Deepmap;//点的深度
        String qidian;//定义起点
        String zhongdian;//定义终点
        String temp;//暂存数据
        int n = list1.size();
        for (int i = 0; i < n; i++) {
            qidian = list1.get(i);//获得起点
            zhongdian = list2.get(i);//获得终点
            int deep1 = (int) deep.get(qidian);//起点深度
            int deep2 = (int) deep.get(zhongdian);//终点深度
            if (deep1 < deep2) {//如果起点深度<终点深度，调整二者位置
                System.out.println("交换起点终点！！！");
                temp = qidian;
                qidian = zhongdian;
                zhongdian = temp;//交换位置
                control.UpdateData("Pipeline", "StartPointName", qidian, "SequenceNumber", i + 1);
                control.UpdateData("Pipeline", "EndPointName", zhongdian, "SequenceNumber", i + 1);
            }
            PipeNo.put(qidian + zhongdian, i + 1);
            PipeStartPointList.add(qidian);
            PipeEndPointList.add(zhongdian);
        }

    }

}
