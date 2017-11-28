/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.pipenet.Func;


/**
 *
 * @author JackHou
 */
public class Opti_Model_Link_Algo_Model {
     private Prepare_for_Opti pfo;

    public Opti_Model_Link_Algo_Model() {
        pfo = new Prepare_for_Opti();
    }

    /**
     * 不需要输入，不用管这个方法,返回的是适应度，供SouSuo类，与GA类调用
     * 
     * 修改这部分的是影响函数
     * 
     */
    public double fitness(double[] X) {
        double fit =  pipenetOutput(X);
        return fit;
    }
    
    /**
     * 适应度函数应该越简单直接越好，使得计算更简单
     * 
     * 方差的做法是方差越大适应度越小，所以会选择倒数
     * 
     * 针对管网模型的适应度算法，目的是最大化产量，值越大的适应度应该越高
     * @param X
     * @return 
     */
    private double pipenetOutput(double[] X){
        int num = X.length;
        double result = 0;
        double y1[] = Curve1(X);//管网模型的输出
        for (int i = 0; i < num; i++) {
            result = result + y1[i];
        }
//        System.out.println("zhyh-单管网模型的适应性函数：" + result);
        return result;
    }

    /**
     * 曲线1，管网
     */
    private double[] Curve1(double[] X) {//输入一个子树全部点名称，点对应产量
        double y[] = pfo.Y1(X);
        return y;
    }
}
