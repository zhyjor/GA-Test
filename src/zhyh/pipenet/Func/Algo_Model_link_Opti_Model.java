/*
 * 
 *本类是本算法包的连接类，需要写入要计算的变量的取值范围以及适应度的判断方法
 */
package zhyh.pipenet.Func;

import java.util.List;
import java.util.ArrayList;
import zhyh.pipenet.test.Opti_Pipenet;


/**
 *
 * @author zhyh
 */
public class Algo_Model_link_Opti_Model {

    private Opti_Model_Link_Algo_Model oa;
    public String target_Opti;//本次优化的膜牧目标
    public double[] Qmax;//目标点集的产量上限，用于GAdecoder类中
    public double[] Qmin;//目标点集的产量下限，用于GAdecoder类中
    public int num;
    public static List<String> well_in_childtree;//名字链表，应该是井的列表
    public double[] pecan_well;//各个井的配产

    public Algo_Model_link_Opti_Model() {
        oa = new Opti_Model_Link_Algo_Model();
        target_Opti = Opti_Pipenet.target_Opti;//本次优化的膜牧目标
        Qmax = Opti_Pipenet.Qmax;//目标点集的产量上限，用于GAdecoder类中
        Qmin = Opti_Pipenet.Qmin;//目标点集的产量下限，用于GAdecoder类中
        well_in_childtree = Opti_Pipenet.well_in_childtree;//目标点集的名称
        num = Qmax.length;
//        getsetPeiCanByType(well_in_childtree);
    }
    
    public void getsetPeiCanByType(List<String> tree_well){
        
        System.out.println("需要配产的井的列表：" + tree_well.toString());
        
        int num = tree_well.size();//得到子树中的全部井.size();
         pecan_well = new double[num];
         double peican = 30000;
        for (int i = 0; i < num; i++) {
            String name = tree_well.get(i);
            //什么井
            switch(name){
                case "LX-101-1H":
                   peican = 9000;
                   break;
                case "LX-101-4H":
                    peican = 6000;
                    break;
                case "LX-101-5H":
                    peican = 23000;
                    break;
                case "LX-102":
                    peican = 15100;
                    break;
                case "LX-102-2D":
                    peican = 3500;
                    break;
                case "LX-102-3H":
                    peican = 15000;
                    break;
                case "LX-103":
                    peican = 43500;
                    break;
                case "LX-103-1D":
                    peican = 15200;
                    break;
                case "LX-103-3H":
                    peican = 24000;
                    break;
                case "LX-103-4D":
                    peican = 3200;
                    break;
                case "LX-104":
                    peican = 4000;
                    break;
                case "LX-104-1H":
                    peican = 7000;
                    break;
                case "LX-104-3D":
                    peican = 8500;
                    break;
                case "LX-104-7D":
                    peican = 5800;
                    break;
                case "LX-105-1H":
                    peican = 24000;
                    break;
                case "LX-105-2D":
                    peican = 3800;
                    break;
                case "LX-105-3D":
                    peican = 14000;
                    break;
                case "LX-4":
                    peican = 12900;
                    break;
                default:
                    peican = 32500;
                    break;
            }
            pecan_well[i] = peican;
        }
    }

    /**
     * 写入变量的适应度判断方法
     */
    public double fitness(double[] X) {
        return oa.fitness(X,pecan_well);
    }
}
