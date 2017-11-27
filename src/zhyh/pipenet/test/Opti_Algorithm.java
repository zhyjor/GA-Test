/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.pipenet.test;

import zhyh.pipenet.Func.Algo_Model_link_Opti_Model;
import zhyh.pipenet.Func.GA_Pipenet;
import java.util.Arrays;
import java.util.Random;

/**
 * @des 算法调用类
 * 
 * @author zhyh
 */
public class Opti_Algorithm {
     static private double[] X;//计算后得到的解
    private int Xnum;//未知数的个数
    static private double[] min;//各个未知数的上下界
    static private double[] max;//各个未知数的上下界
    private int randnum = 1000;//每次随机的点数
    private boolean change;//判断解是否发生变化
    private int dai = 100;//计算多少代
    static public double fitness;//计算后的解的适应值
    private double[] R;//搜索半径最大值
    final static private int dengfenqujian = 10000;//将每个区间等分成这些份，然后随机
    static private int xiancheng = Runtime.getRuntime().availableProcessors() - 1;//启动的线程数，默认为CPU核数-1;//启动的线程数，默认为CPU核数-1
    public double GAFitness;//遗传算法得到的适应值
    private Algo_Model_link_Opti_Model lam;

    /**
     * 构造方法，输入参数的上下界集合
     */
    public Opti_Algorithm() {
        change = false;
        GAFitness = 0;
        fitness = 0;
        lam = new Algo_Model_link_Opti_Model();
        this.max = Opti_Pipenet.Qmax;
        this.min = Opti_Pipenet.Qmin;
        System.out.println("该计算机为 " + xiancheng + "核处理器");
        if (xiancheng == 0) {
            xiancheng = 1;
        }
        this.Xnum = max.length;
        R = new double[Xnum];
    }

    /**
     * 多线程计算遗传算法，得到的解再带入粒子群算法提高精度
     */
    synchronized private void GAthread() throws InterruptedException {//采用多线程同时计算多次提高精度
        Thread thread[] = new Thread[xiancheng];

        for (int i = 0; i < xiancheng; i++) {
            thread[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    new GA_Pipenet().start();
                }
            });
            thread[i].start();
        }
        System.out.println("GA线程启动完毕！");
        for (int i = 0; i < xiancheng; i++) {//等待子线程结束
            thread[i].join();
        }
    }

    private void finalGAResult() throws InterruptedException {//由于采用了多线程，要判断是否各个线程均结束了
        GAthread();
        System.out.println("GA:全部线程结束！");
        GAFitness = GA_Pipenet.finalFitness;
        inputX(GA_Pipenet.bestResult);//将GA输出的结果输入Liziqun算法
    }

    private void inputX(double[] X) {
        this.X = copyX(X);
        fitness = fitness(this.X);
        System.out.println("GA遗传算法得到最优解为：X= " + Arrays.toString(X) + "\nGA的适应值为：Y= " + fitness + " ;");
        System.out.println("----------------GA完成-----------------");
    }


    /**
     * 复制数组
     */
    synchronized private double[] copyX(double[] X) {
        return Arrays.copyOf(X, Xnum);
    }

  
   

    /**
     * 本类的计算启动方法
     */
    public void start() throws InterruptedException {
        finalGAResult();//先用GA粗略计算，再用粒子群提高精度
    }


    /**
     * 粒子群的适应度评价函数,这里用于CossPoint类中
     */
    synchronized public double fitness(double[] X) {
        return lam.fitness(X);
    }

    public double[] finalResult_X() {
        return X;
    }
}
