/*
 *本类是计算优化中的约束条件的模型方法集合
 */
package zhyh.Model.CoalSeam.preparedata;

/**
 * 煤粉脱落及支撑剂脱落的流速计算 《煤层气井产气通道内煤粉运动特征分析》——綦耀光 《单相流煤层气井裂隙煤粉受力分析及启动条件》——张芬娜
 *
 * @author 武浩
 */
public class Velocity_Sand_production {

    private double rous = 1.5;//煤粉密度:g/cm3
    private double rouf = 1.0;//水密度：g/cm3
    private double Rs;//煤粉半径,微米
    private double R;//骨架半径
    private double d;//两煤粉间最短距离（统计值）
    private double V;//流体表观流速
    private double Fg;//煤粉受到重力

    private void Fg() {
        
    }

}
