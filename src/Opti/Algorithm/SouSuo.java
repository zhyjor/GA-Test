/*
 * GA算法的后续计算，类似粒子群算法原理，用来进一步提高精度
 */
package Opti.Algorithm;

import Opti.Functions.OptiAll;
import java.util.Arrays;
import java.util.Random;

/**
 * 二次搜索算法，已经调用遗传算法，所以不必对遗传算法特别操作，可以适当调整一下GA中的参数
 *
 * @author 武浩
 */
public class SouSuo {

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
    private Algorithm_Model_link_Opti_Model lam;

    /**
     * 构造方法，输入参数的上下界集合
     */
    public SouSuo() {
        change = false;
        GAFitness = 0;
        fitness = 0;
        lam = new Algorithm_Model_link_Opti_Model();
        this.max = OptiAll.Qmax;
        this.min = OptiAll.Qmin;
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
                    new GA().start();
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
        GAFitness = GA.finalFitness;
        inputX(GA.bestResult);//将GA输出的结果输入Liziqun算法
    }

    private void inputX(double[] X) {
        this.X = copyX(X);
        fitness = fitness(this.X);
        System.out.println("GA遗传算法得到最优解为：X= " + Arrays.toString(X) + "\nGA的适应值为：Y= " + fitness + " ;");
        System.out.println("----------------下面带入粒子群算法进一步计算-----------------");
    }

    /**
     * 确保输出时的线程安全，粒子群算法更优解判断
     */
    synchronized private void threadoutput(double[] newX) {
        double temp = fitness(newX);
        if (temp > fitness) {//如果新解的适应度更好,更新指标
            X = copyX(newX);//新的解
            fitness = temp;//新的适应度
            change = true;//标准发生变化
        }
    }

    /**
     * 复制数组
     */
    synchronized private double[] copyX(double[] X) {
        return Arrays.copyOf(X, Xnum);
    }

    /**
     * 在搜索半径内随机搜索更好的解
     */
    private void findnewX(double r[]) {//输入搜索半径
        double[] X = copyX(this.X);
        double xiajie = 0;//搜索半径下界
        double shangjie = 0;//搜索半径上界
        double newX[] = new double[Xnum];//新搜索得到的解
        for (int j = 0; j < randnum; j++) {//随机一些点
            for (int i = 0; i < Xnum; i++) {//每个点的位置
                xiajie = X[i] - r[i];
                shangjie = X[i] + r[i];
                if (xiajie < min[i]) {//如果越界就等于边界
                    xiajie = min[i];
                }
                if (shangjie > max[i]) {
                    shangjie = max[i];
                }
                newX[i] = xiajie + random() * (shangjie - xiajie);
            }
            threadoutput(newX);//测试新解的适应度如何
        }
    }

    /**
     * 多线程同时计算提高精度，输入搜索半径
     */
    private void Liziqunthread(double r[]) throws InterruptedException {//采用多线程计算提高精度
        Thread[] thread = new Thread[xiancheng];
        for (int i = 0; i < xiancheng; i++) {//启动多线程
            thread[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    findnewX(r);
                }
            });
            thread[i].start();
        }
        for (int i = 0; i < xiancheng; i++) {
            thread[i].join();
        }

    }

    /**
     * 本类的计算启动方法
     */
    public void start() throws InterruptedException {
        finalGAResult();//先用GA粗略计算，再用粒子群提高精度
        double percent = 1.0;
        for (int i = 0; i < Xnum; i++) {//初始化半径
            R[i] = Math.abs(max[i] - min[i]) / 2.0;
        }
        double r[] = new double[Xnum];//每次实际搜索的半径
        for (int j = 0; j < dai; j++) {//搜索多次，每次收缩搜索范围
            percent = (dai * 1.0 - j) / dai;
            for (int i = 0; i < Xnum; i++) {//逐渐缩小半径
                r[i] = R[i] * percent;
            }
            Liziqunthread(r);//搜索更好的解
            while (change) {//如果发生变化，就以新起点再搜一次
                change = false;
                Liziqunthread(r);
            }
        }

        System.out.println("粒子群最终结果：" + Arrays.toString(X));
    }

    synchronized private static double random() {//为了确保随机数最小间隔，采用整数随机换算
        return (new Random().nextInt(dengfenqujian) * 1.0 / dengfenqujian);
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
