/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipe_model;

import Data.MapStorage.Starter_third;

/**
 *
 * @author JackHou
 */
public class UpdateQ {

    public static void main(String args[]){
        Starter_third st = new Starter_third();
        st.starter3(false);//FALSE是关闭树结构显示界面
        Start_Qmax_Qmin sqq = new Start_Qmax_Qmin();
        sqq.input_wellList();
    }
    
}
