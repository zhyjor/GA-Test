/*
 *本类是启动程序的起点，类名中的数字是运行顺序，不能乱。
 *计算出地层模型得出的每个井对应的最大最小产量，并更新到数据库中，其中地层模型是没有后续用处的，仅仅更新一下数据库，但是更新的数据库是必要的
 */
package Background_functions;

import Data.MapStorage.Starter_third;
import Model.CoalSeam.Qmax_Qmin.Updata_database_Pwf_Q_Constraint;
import Model.CoalSeam.preparedata.Update_Pr_shouldbe;
import Model.Pipe.Update_Pipe_Mozuxishu_PipeCL;

/*
 * 本类是整个系统的启动运行开段
 * @author 武浩
 */
public class Key1_Updater1 {

    private static boolean show_tree = false;//是否启动树结构图像显示功能,true:显示，false：不显示
    private Starter_third st;//数据读
    
    private Update_Pipe_Mozuxishu_PipeCL upp;//计算管道CL,为莫斯摩阻系数，只与管道本身相关的摩阻系数，所以不需要经常更新
    private Updata_database_Pwf_Q_Constraint uc;//单井的产能界限
    private Update_Pr_shouldbe UPr;//更新数据库Pr_shouldbe列,更新数据库

    /**
     * 该部分都是独立的功能方法，都是直接操作数据库，更新完数据后不需要直接运行
     */
    protected void auxiliary_functions() {

        st = new Starter_third();
        st.starter3(show_tree);//读入数据
        UPr = new Update_Pr_shouldbe();
        UPr.Pr_shouldbe();
        upp = new Update_Pipe_Mozuxishu_PipeCL();
        upp.mozu();//根据各条管道参数，计算其水力摩阻系数以及优化中用到的常数，输入数据库，
        uc = new Updata_database_Pwf_Q_Constraint();
        uc.update();//根据地层数据，计算各个井的井底压力与产量上下限，输入数据库

    }

    public static void main(String[] args) {
        Key1_Updater1 key = new Key1_Updater1();
        key.auxiliary_functions();
    }
}
