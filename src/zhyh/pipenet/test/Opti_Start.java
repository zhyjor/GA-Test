/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.pipenet.test;
/**
 *
 * @author JackHou
 */
public class Opti_Start {
    /**
     * 数据录入完毕后，启动该方法进行优化
     */
    public void starter() throws InterruptedException {
        System.out.println("zhyh-启动优化函数 ");
        new Opti_Pipenet().optstart();
    }
}
