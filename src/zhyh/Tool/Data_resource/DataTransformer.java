/*
 * 本类主要用于ResultSet 转换数据类型，
 */
package zhyh.Tool.Data_resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据转换类型方法集合
 *
 * @author 武浩
 */
public class DataTransformer {

    /**
     * 读取数据库中的一列数据，将其转化为List<String>
     * DubiaoString（表名称，列名称）
     *
     * @param liename
     * @return
     */
    public List<String> ToString(ResultSet set, String liename) {
        try {
            List<String> list = new ArrayList();
            set.beforeFirst();//指针归位
            while (set.next()) {
                String data = set.getString(liename);
                if (data == null || "".equals(data)) {//过滤空值，防止报错
                    data = "0";
                }
                list.add(data);
            }
            return list;
        } catch (SQLException ex) {
            System.out.println("结果集合ResultSet或列名输入有误，没有发现相关查询集合！");
        }
        return null;
    }

    /**
     * ResultSet 转 List<Integer>
     */
    public List<Integer> ToInt(ResultSet set, String liename) {
        try {
            List<Integer> list = new ArrayList();
            set.beforeFirst();//指针归位
            while (set.next()) {
                String data = set.getString(liename);
                if (data == null || "".equals(data)) {//过滤空值，防止报错
                    data = "0";
                }
                list.add(Integer.parseInt(data));
            }
            return list;
        } catch (SQLException ex) {
            System.out.println("查询名称输入有误，没有发现相关查询集合！");
        }
        return null;
    }

    /**
     * 读取数据库中的一列数据，将ResultSet其转化为List<Double>
     * DubiaoDouble（表名称，列名称）
     *
     *
     */
    public List<Double> ToDouble(ResultSet set, String liename) {
        try {
            //DubiaoDouble（表名称，列名称）得到表中的某一列的数据,直接转换为double
            List<Double> list = new ArrayList();
            set.beforeFirst();//指针归位
            while (set.next()) {
                String data = set.getString(liename);
                if (data == null || "".equals(data)) {//过滤空值，防止报错
                    data = "0";
                }
                list.add(Double.parseDouble(data));
            }
            return list;
        } catch (SQLException ex) {
            System.out.println("查询名称输入有误，没有发现相关查询集合！");
        }
        return null;
    }

    /**
     * String数组转double数组
     */
    public double[] StringtoDouble(String[] value) {
        int n = value.length;
        double[] temp = new double[n];
        for (int i = 0; i < n; i++) {
            temp[i] = Double.parseDouble(value[i]);
        }
        return temp;
    }

}
