/*
 * 遗传算法类
 */
package zhyh.Opti.Algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.BinaryMutation;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ElitisticListPopulation;
import org.apache.commons.math3.genetics.FixedGenerationCount;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.OnePointCrossover;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.StoppingCondition;
import org.apache.commons.math3.genetics.TournamentSelection;

/**
 * 遗传算法采用java.commons.math进行计算
 *
 * @author 武浩
 */
public class GA {

    private GAdecoder co;//算法编码解码器
    private String root;
    private static int DIMENSION;//编码总量
    private static int POPULATION_SIZE = 150;//种群个体数量
    private static int NUM_GENERATIONS = 200;//停止计算最大代数
    private static double ELITISM_RATE = 0.2;//精英选择率
    private static double CROSSOVER_RATE = 1.0;//交叉概率
    private static double MUTATION_RATE = 0.1;//变异概率
    public static double[] bestResult;//多线程共有的，最终输出结果
    public static double finalFitness = 0;//多线程共有的

    private Algorithm_Model_link_Opti_Model amlm;//连接计算模型的类

    public GA() {
        co = new GAdecoder();
        DIMENSION = GAdecoder.num * GAdecoder.weishu;
        amlm = new Algorithm_Model_link_Opti_Model();
        bestResult = new double[amlm.num];
        finalFitness = 0;
    }

    public void start() {

        GeneticAlgorithm ga = new GeneticAlgorithm(
                new OnePointCrossover<>(),
                CROSSOVER_RATE, // all selected chromosomes will be recombined (=crosssover)
                new BinaryMutation(),
                MUTATION_RATE,
                new TournamentSelection((int) (POPULATION_SIZE / 2))
        );

        //初始种群
        Population initial = randomPopulation();
        //停止条件，最大代数
        StoppingCondition stopCond = new FixedGenerationCount(NUM_GENERATIONS);

        // 最优初始个体
        Chromosome bestInitial = initial.getFittestChromosome();
        //开始计算
        Population finalPopulation = ga.evolve(initial, stopCond);

        //最终种群中最好个体
        Chromosome bestFinal = finalPopulation.getFittestChromosome();
        String code = bestFinal.toString();
        char[] Code = code.toCharArray();
        List<Integer> bestresult = new ArrayList();
        boolean tf = false;
        for (char o : Code) {
            if (o == ']') {
                tf = false;
            }
            if (tf) {
                if (o == '1') {
                    bestresult.add(1);
                }
                if (o == '0') {
                    bestresult.add(0);
                }
            }
            if (o == '[') {
                tf = true;
            }
        }
        double[] X = co.DNAreader(bestresult);
        double fit = new GA.MaxValue(bestresult).fitness();
        output(fit, X);
    }

    /**
     * 多线程与共有的适应度以及结果比较与赋值
     */
    synchronized void output(double fit, double[] X) {
        if (fit > finalFitness) {
            bestResult = X;
            finalFitness = fit;
        }
    }

    /**
     * 随机一个初始种群
     */
    synchronized private ElitisticListPopulation randomPopulation() {
        List<Chromosome> popList = new LinkedList<>();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            BinaryChromosome randChrom = new GA.MaxValue(BinaryChromosome.randomBinaryRepresentation(DIMENSION));
            popList.add(randChrom);
        }
        return new ElitisticListPopulation(popList, popList.size(), ELITISM_RATE);
    }

    /**
     * Chromosomes represented by a binary chromosome.
     *
     * The goal is to set all bits (genes) to 1.
     */
    public class MaxValue extends BinaryChromosome {

        public MaxValue(List<Integer> representation) {
            super(representation);
        }

        /**
         * Returns number of elements != 0
         */
        @Override
        public double fitness() {
            List<Integer> r = this.getRepresentation();
            double[] X = co.DNAreader(r);
            return amlm.fitness(X);
        }

        @Override
        public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> chromosomeRepresentation) {
            return new MaxValue(chromosomeRepresentation);
        }

    }
}
