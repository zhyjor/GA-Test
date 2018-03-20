/*
 * 集输管网系统评价
 */
package Background_functions;

import Data.MapStorage.Starter_third;
import Estimater.Estimater_Equipment;
import Estimater.Estimater_Wellmaching;

/**
 *
 * @author 武浩
 */
public class Key4_Estimater1 {

    private Estimater_Wellmaching esc;
    private Starter_third st;
    private Estimater_Equipment ee;

    /**
     * 启动评价图，
     */
    private void estimate() {
        st = new Starter_third();
        st.starter3(false);//FALSE是关闭树结构显示界面
        esc = new Estimater_Wellmaching();
        esc.est();
        ee = new Estimater_Equipment();
        ee.estimate();
    
    }

    public static void main(String[] args) {
        new Key4_Estimater1().estimate();
    }
}
