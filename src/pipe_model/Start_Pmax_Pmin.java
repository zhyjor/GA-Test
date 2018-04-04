/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipe_model;

/**
 * 查询各个井的约束条件
 *
 * @author zhyh
 */
public class Start_Pmax_Pmin {
    
    protected double Pmax;
    protected double Pmin;

    public Start_Pmax_Pmin() {//构造方法

    }

    /**
     * 初始化,输入井名确定压力范围
     */
    protected void input_wellname(String o) {
        Pmax = 4000000;
        Pmin = 2000000;
    }

}
