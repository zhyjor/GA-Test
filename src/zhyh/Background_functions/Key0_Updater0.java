/*
 * 根据填入的点数据，更新管道起终点的P，Q，T
 */
package zhyh.Background_functions;

import zhyh.Data.MapStorage.Starter_third;
import zhyh.Model.PipeNet.Update_pipe_data1;

/**
 * 根据填入的数据更新管道起终点P、T、Q，以及其他表中的Q_shouldbe
 *
 * @author 武浩
 */
public class Key0_Updater0 {

    private static boolean show_tree = true;//是否启动树结构图像显示功能,true:显示，false：不显示
    static private Update_pipe_data1 up;
    static private Starter_third st;//数据读

    static protected void update() {
        st = new Starter_third();
        st.starter3(show_tree);
        up = new Update_pipe_data1();
        up.update();
    }

    public static void main(String[] args) {
        update();
    }
}
