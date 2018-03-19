/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Background_functions;

import zhyh.Data.MapStorage.Starter_third;
import zhyh.Estimater.Estimater_Well_InfluenceRate;
import zhyh.Tool.Data_resource.DBcontroller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 武浩
 */
public class Key6_Estimater2 {

    private Estimater_Well_InfluenceRate ewi;
    private List<Double> deltaQ;
    private List<String> name_for_estimation;
    private Starter_third st;
    private List<String> welllist_for_show;
    private List<Double> data_for_show;
    public String[] groupname;

    public String picturn_title = "煤层气井对全局影响评价图";//弹窗的标题
    public List<String> membername;//评价对象——井、或者管道等等
    public List<Double>[] data;//每组数据的具体参数
    public DBcontroller db = new DBcontroller();
    private Key5_Shower key5;
    private String region = "";
    private Map<String, String> well_region_map;
//
//    private void estimate(List<String> namelist, String region) throws InterruptedException {
//        this.region = region;
//        estimate(namelist);
//    }

    public void estimate(List<String> namelist, String region) throws InterruptedException {
        st = new Starter_third();
        st.starter3(false);
        ewi = new Estimater_Well_InfluenceRate();
        name_for_estimation = namelist;
        ewi.estimateWell(namelist);

        deltaQ = new ArrayList();
//        String qimingzi = "";
//        
//        for (String o : namelist) {
//            qimingzi = qimingzi + o + "、";
//        }
        groupname = new String[1];
        groupname[0] = "井：" + namelist + " 对其他井的产能影响系数（%）";
        key5 = new Key5_Shower(groupname);

        List<Double> tempdata;
        tempdata = ewi.result_est;
        List<String> tempname;
        tempname = ewi.namelist_inOpti;
        well_region_map = ewi.well_region_map;

        welllist_for_show = new ArrayList();
        data_for_show = new ArrayList();
        int num = tempname.size();
        String name;
        data = new List[1];
        groupname = new String[num - namelist.size()];

        for (int i = 0; i < num; i++) {
            name = tempname.get(i);
            if (region.equals("")) {
                if (!namelist.contains(name)) {//排除自身
                    welllist_for_show.add(name);
                    data_for_show.add(tempdata.get(i));
                    deltaQ.add(ewi.deltaQ.get(i));
                }
            } else {
                if (!namelist.contains(name) && well_region_map.get(name).equals(region)) {//排除自身,并且所属区域无误
                    welllist_for_show.add(name);
                    data_for_show.add(tempdata.get(i));
                    deltaQ.add(ewi.deltaQ.get(i));
                }
            }
        }
        key5.membername = welllist_for_show;
        List[] temp = new List[1];
        temp[0] = data_for_show;
        key5.data = temp;
        key5.picturn_title = "单井的全局影响评价图";
        key5.estimate_show();

    }

    public static void main(String[] args) throws InterruptedException {
        List<String> well = new ArrayList();
        well.add("A1-011");
        new Key6_Estimater2().estimate(well, "");//region=“”时默认为显示全部区域的井，

    }
}
