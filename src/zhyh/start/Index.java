/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.start;

import zhyh.Data.MapStorage.Starter_third;
import zhyh.pipenet.test.Opti_Start;

/**
 *
 * @author zhyh
 */
public class Index {
    private Opti_Start start6;
    private Starter_third st;
    public static boolean True_or_Ideal = false;//优化是否使用现场实际CL值
    /**
     * 启动优化程序
     */
    private void Opti(boolean TF) throws InterruptedException {
        Index.True_or_Ideal = TF;
        st = new Starter_third();
        st.starter3(TF);//FALSE是关闭树结构显示界面
        start6 = new Opti_Start();
        start6.starter();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("----------------管网数据遗传算法优化开始-----------------");
//        System.out.println(Math.asin(10>>4/3));
        new Index().Opti(false);//false优化时不使用现场实际CL值，使用理论值
    }
}