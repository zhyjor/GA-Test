/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background_functions;

import Data.MapStorage.Starter_third;
import Model.PipeNet.Update_P_shouldbe;
import Model.PipeNet.Update_pipe_data1;
import Model.PipeNet.Update_pipe_data2;
import Model.PipeNet.Update_pipe_data3;

/**
 *
 * @author 武浩
 */
public class Key3_Updater2 {

    static private Update_P_shouldbe UP;
    static private Starter_third st;//数据读
    static private Update_pipe_data2 upd2;
    static private Update_pipe_data3 upd3;

    private static void init() {
        st = new Starter_third();
        st.starter3(false);//FALSE是关闭树结构显示界面
        UP = new Update_P_shouldbe(false);//更新CL后才能使用,false不使用现场Real_Pipe_CL实际数据，用理论值
        upd2 = new Update_pipe_data2();
        upd2.update(false);//false是不使用现场的实际数据
        upd3 = new Update_pipe_data3();
    }

    public static void main(String[] args) {
        init();

    }
}
