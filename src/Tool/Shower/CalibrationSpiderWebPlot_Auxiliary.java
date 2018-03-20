/*
 * 将数据整理生成CategoryDataset类型，供CalibrationSpiderWebPlot_User类使用
 */
package Tool.Shower;

import Data.InputandClassify.DynamicData3;
import Data.InputandClassify.StaticData2;
import Data.MapStorage.StaticDataMap7;
import Tool.Data_resource.DBcontroller;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 蛛网图数据整理录入类
 *
 * @author 武浩
 */
public class CalibrationSpiderWebPlot_Auxiliary {

    private DBcontroller db;
    private List<Double> well_matching_rate;//井的总匹配性
    private List<Double> well_pipe_match;//井与管的匹配性
    private List<String> wellname;//井的名称列表
    private List<String> pipe_start_name;//管道起点
    private List<String> pipe_end_name;//管道终点
    private List<Double> real_trans_rate;//管道实际输送效率
    private List<Double> opti_trans_rate;//优化后的输送效率
    private ResultSet re;
    private List<String> namelist;//只显示某一地区的数据，数据库中Region列的
    private String region;
    private boolean bushaixuan = true;

    public CalibrationSpiderWebPlot_Auxiliary() {
        well_matching_rate = StaticData2.Well_matching;
        wellname = StaticData2.getWellName();
        well_pipe_match = StaticData2.Well_Pipe_matching;
        pipe_start_name = StaticDataMap7.PipeStartPointList;
        pipe_end_name = StaticDataMap7.PipeEndPointList;
        real_trans_rate = DynamicData3.Real_trans_rate;
        opti_trans_rate = DynamicData3.Opti_trans_rate;
        namelist = new ArrayList();
    }

    public CalibrationSpiderWebPlot_Auxiliary(String filename, String region) {
        db = new DBcontroller();
        well_matching_rate = StaticData2.Well_matching;
        wellname = StaticData2.getWellName();
        well_pipe_match = StaticData2.Well_Pipe_matching;
        pipe_start_name = StaticDataMap7.PipeStartPointList;
        pipe_end_name = StaticDataMap7.PipeEndPointList;
        real_trans_rate = DynamicData3.Real_trans_rate;
        opti_trans_rate = DynamicData3.Opti_trans_rate;
        re = db.getFile(filename);
        this.namelist = db.ReadString(re, "Region");
        this.region = region;
        bushaixuan = false;
    }
    private List<Boolean> temp = new ArrayList();

    /**
     * 看所属地区是否符合要求
     */
    private List<Boolean> numlist(String region, List<String> Region) {

        for (String o : Region) {
            if (region.equals(o)) {
                temp.add(true);
            } else {
                temp.add(false);
            }
        }
        return temp;
    }

    /**
     * 筛选地区
     */
    private CategoryDataset Dataset(List<String> membername, String[] groupname, List<Double>[] data, List<Boolean> region) {

        List<Boolean> temp = new ArrayList();
        temp = numlist(this.region, namelist);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int gruopnum = groupname.length;
        int membernum = membername.size();
        List<Double> temp_data = new ArrayList();
        String group;
        for (int i = 0; i < gruopnum; i++) {
            temp_data = data[i];
            int num = temp_data.size();
            group = groupname[i];
            for (int j = 0; j < num; j++) {
                if (temp.get(j)) {
                    dataset.addValue(temp_data.get(j), group, membername.get(j));
                }
            }
        }
        return dataset;
    }

    public CategoryDataset Dataset(List<String> membername, String[] groupname, List<Double>[] data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (bushaixuan) {
            int gruopnum = groupname.length;
            int membernum = membername.size();
            List<Double> temp_data = new ArrayList();
            String group;
            for (int i = 0; i < gruopnum; i++) {
                temp_data = data[i];
                int num = temp_data.size();
                group = groupname[i];
                for (int j = 0; j < num; j++) {
                    dataset.addValue(temp_data.get(j), group, membername.get(j));
                }
            }
        } else {
            dataset = (DefaultCategoryDataset) Dataset(membername, groupname, data, temp);
        }
        return dataset;
    }

}
