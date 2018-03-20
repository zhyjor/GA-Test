/*
 * 单独的功能，更新数据库中的管道摩阻系数，威莫斯公式
 */
package Model.Pipe;

import Data.InputandClassify.NameList4;
import Data.MapStorage.StaticDataMap7;
import static Data.MapStorage.StaticDataMap7.Gomap;
import Tool.Data_resource.DBcontroller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 为莫斯摩阻系数，只与管道本身相关的摩阻系数，所以不需要经常更新
 *
 * @author 武浩
 */
public class Update_Pipe_Mozuxishu_PipeCL {

    static private DBcontroller db = new DBcontroller();
    static private List<Integer> xuhao = new ArrayList();//管道的序号
    static public List<Double> mozu = new ArrayList();//管道的摩阻系数
    static public List<Double> pipe_cl = new ArrayList();//管道的CL常数
    static public List<Double> real_pipe_cl = new ArrayList();//管道的CL常数
    private static Map<String, Integer> No = new HashMap();//管道编号
    public static Map<String, Double> PipeCL = new HashMap();
//    public static Map<String, Double> Real_PipeCL = new HashMap();
    private Update_PipeCL up = new Update_PipeCL();

    /*
     * 利用威莫斯公式计算各个管道的水力摩阻系数
     */
    public void mozu() {//全部是标准单位
        No = StaticDataMap7.PipeNo;
        double D;
        String zhongdian;
        String pipename;
        double mozu = 0;
        for (String o : NameList4.namelistPipe) {//遍历全部点
            zhongdian = Gomap.get(o);//某点的下游点
            if (zhongdian == null) {
                continue;
            }
            pipename = o + zhongdian;
            try {//防止因无管径数据而报错
                D = StaticDataMap7.PipeDmap.get(pipename) / 1000.0;
                mozu = 0.009407 / Math.pow(D, 0.333);//威莫斯公式
            } catch (Exception e) {
                System.out.println("请输入管道" + pipename + "的直径！");
                mozu = 0.009407 / Math.pow(0.1, 0.333);//威莫斯公式，默认管径0.1m
            }
            xuhao.add(No.get(pipename));
            Update_Pipe_Mozuxishu_PipeCL.mozu.add(mozu);
            double c1 = up.Real_PipeCL(o, zhongdian);//仅当管道生产数据真实可靠时用
            PipeCL.put(o+zhongdian, c1);
            double c2 = up.Ideal_PipeCL(o, zhongdian, mozu);//理想状态的
            real_pipe_cl.add(c1);
            pipe_cl.add(c2);
        }
        db.UpdateData("Pipeline", "mozu_para", Update_Pipe_Mozuxishu_PipeCL.mozu, "SequenceNumber", xuhao);
        db.UpdateData("Pipeline", "Pipe_CL", Update_Pipe_Mozuxishu_PipeCL.pipe_cl, "SequenceNumber", xuhao);
        System.out.println("Pipe_CL：："+Update_Pipe_Mozuxishu_PipeCL.pipe_cl);
        db.UpdateData("Pipeline", "Real_Pipe_CL", Update_Pipe_Mozuxishu_PipeCL.real_pipe_cl, "SequenceNumber", xuhao);
        System.out.println("Update_Pipe_Mozuxishu:管道摩阻系数更新完毕！！");
    }

}
