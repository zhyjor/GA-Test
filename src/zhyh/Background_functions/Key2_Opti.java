/*
 * 数据修改完毕即可启动Opti功能，并将最后结果通过Opti_Result_Storager类整理输出到数据库
 */
package zhyh.Background_functions;

import zhyh.Data.MapStorage.Starter_third;
import zhyh.Opti.Functions.Starter6;

/**
 *
 * @author 武浩
 */
public class Key2_Opti {

    private Starter6 start6;
    private Starter_third st;
    public static boolean True_or_Ideal = false;//优化是否使用现场实际CL值

    /**
     * 启动优化程序
     */
    private void Opti(boolean TF) throws InterruptedException {
        Key2_Opti.True_or_Ideal = TF;
        st = new Starter_third();
        st.starter3(false);//FALSE是关闭树结构显示界面
        start6 = new Starter6();
        start6.starter();
    }

    public static void main(String[] args) throws InterruptedException {
        new Key2_Opti().Opti(false);//false优化时不使用现场实际CL值，使用理论值
    }
}
