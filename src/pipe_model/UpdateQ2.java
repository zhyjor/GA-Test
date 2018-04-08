/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipe_model;

import Data.MapStorage.Starter_third;



/**
 *
 * @author 武浩
 */
public class UpdateQ2 {

   
    static private Starter_third st;//数据读
    static private Theory_Qmax_Qmin upd2;
 
    private static void init() {
        st = new Starter_third();
        st.starter3(false);//FALSE是关闭树结构显示界面
       
        upd2 = new Theory_Qmax_Qmin();
        upd2.update(false);//false是不使用现场的实际数据
       
    }

    public static void main(String[] args) {
        init();

    }
}
