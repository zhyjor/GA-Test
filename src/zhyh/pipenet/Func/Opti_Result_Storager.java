/*
 * 用来存储优化结果，等待后续处理，用于OptiAll类中
 */
package zhyh.pipenet.Func;

import Opti.Functions.*;
import Data.InputandClassify.NameList4;
import Data.InputandClassify.StaticData2;
import Data.MapStorage.DynamicDataMap8;
import Data.MapStorage.StaticDataMap7;
import Model.CoalSeam.preparedata.Best_Pwf;
import Tool.Data_resource.DBcontroller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 武浩
 */
public class Opti_Result_Storager {

    /**
     * 这三个是由OptiAll类推送过来的
     */
    public static List<String> welllist;//井列表，暂时存储用，最终都输入至allwelllist中
    public static double[] Q;//优化结果数组，暂时存储用，最终都输入至allresult_Q中
    public static List<String> allpointlist;//子树全部点列表
    /**
     * 下面值由本类程序获得
     */
    private static DBcontroller db;
    public static List<String> allwelllist;
    public static List<Double> allresult_Q;
    private static List<Double> allresult_P;
    private Best_Pwf bp = new Best_Pwf();
    private Map<String, Double> Opti_PipeQ;//管道流量累加，起点名搜索
    List<Double> Qpipe;//优化得到的管道输量
    Map<String, Double> RealQ;//管道实际输量的查询

    public Opti_Result_Storager() {
        RealQ = DynamicDataMap8.RealQ;
        allwelllist = new ArrayList();
        allresult_Q = new ArrayList();
        allresult_P = new ArrayList();
        welllist = new ArrayList();
        db = new DBcontroller();
        Qpipe = new ArrayList();
        Opti_PipeQ = new HashMap();

    }

    /**
     * 每优化完一个子树，运行一次，将优化结果存在上面变量wellQ中
     */
    public void storageResult() {
        int num = welllist.size();
//        System.out.println("Opti_Result_Storager::" + welllist + "优化完毕！！最终结果：" + Arrays.toString(Q));
        String name;
        double q;
        for (int i = 0; i < num; i++) {
            name = welllist.get(i);
            q = Q[i];
            allwelllist.add(name);
            allresult_Q.add(q);
            allresult_P.add(bp.pwf(name, q));
        }
    }

    /**
     * 结果输入数据库
     */
    public void output_to_database() {
        db.UpdateData("Well", "Best_Q", allresult_Q, "Name", allwelllist);
        db.UpdateData("Well", "Best_Pwf", allresult_P, "Name", allwelllist);
        String pipe;
        Sum_PipeQ(allwelllist, allresult_Q);
        List<Integer> No = new ArrayList();
        int i = 0;
        for (String o : StaticDataMap7.PipeStartPointList) {
            Qpipe.add(Opti_PipeQ.get(o));
            pipe = o + StaticDataMap7.Gomap.get(o);
            i++;
            No.add(i);
        }

        db.UpdateData("Pipeline", "Opti_Q", Qpipe, "SequenceNumber", No);
        update_otherfile();
    }

    /**
     * 管道流量累加，用于更新数据库中优化后的各条管道的流量
     */
    private void Sum_PipeQ(List<String> allwelllist, List<Double> allresult_Q) {
//        System.out.println("输入Sum_PipeQ：：" + allwelllist + "--" + allresult_Q);
        Map<String, Double> pointQ = new HashMap();
        int num = allresult_Q.size();
        for (int i = 0; i < num; i++) {
            pointQ.put(allwelllist.get(i), allresult_Q.get(i));
        }
        List<String> albelong;
        for (String o : NameList4.namelistPipe) {
            albelong = new ArrayList();
            albelong.addAll(StaticDataMap7.Allbelongmap.get(o));
            albelong.add(o);
            double leijia = 0;
            for (String p : albelong) {
                try {
                    leijia = leijia + pointQ.get(p);
                } catch (Exception e) {
                    leijia = leijia + 0;
                }
            }
            Opti_PipeQ.put(o, leijia);
        }

    }

    private void update_otherfile() {
        List<Double> temp = new ArrayList();
        for (String o : StaticData2.getValveName()) {
            temp.add(Opti_PipeQ.get(o));
            System.out.println("阀组-"+o+":" + Opti_PipeQ.get(o));
        }
        db.UpdateData("Valve", "Opti_Q", temp, "Name", StaticData2.getValveName());
        temp = new ArrayList();
        for (String o : StaticData2.getStationName()) {
            temp.add(Opti_PipeQ.get(o));
        }
        db.UpdateData("Station", "Opti_Q", temp, "Name", StaticData2.getStationName());

        temp = new ArrayList();
        for (String o : StaticData2.getCenterStationName()) {
            temp.add(Opti_PipeQ.get(o));
        }
        db.UpdateData("CenterStation", "Opti_Q", temp, "Name", StaticData2.getCenterStationName());
    }
}
