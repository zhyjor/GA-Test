/*
 *本类是将数据库中的数据表读入存储的类，用于StaticData2、DynamicData3中
 */
package zhyh.Data.InputandClassify;

import zhyh.Tool.Data_resource.DBcontroller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * 数据库中全部数据读入储存的类，
 * 启动次序：1
 *
 * @author 武浩
 */
public class AllData1 {

    /**
     * 数据库的连接值,访问数据库必须填写
     */
    public static final String DBDRIVER = "org.gjt.mm.mysql.Driver";//数据库驱动、
    static public String DBURL;//地址URL
    static public String DBUSER;//用户名
    static public String DBPASS;//密码

    /**
     * 一次性读入全部数据，分类整理
     */
    static public ResultSet WellSet;//《井》数据查询集合
    static public ResultSet ValveSet;//《阀组》数据查询集合
    static public ResultSet StationSet;//《集气站》数据查询集合
    static public ResultSet CenterStationSet;//《中央处理厂》数据查询集合
    static public ResultSet EquipmentSet;//《增压设备》数据查询集合
    static public ResultSet PipeSet;//《管道》数据查询集合
    static public ResultSet CoalSeamSet;//《煤层》数据查询集合

    private DBcontroller control = new DBcontroller(); //自己编写的数据库的操作类，包括增删查改等

    /**
     * 这里给上面的变量赋值，通过DBcontroller读入数据库中全部6张表，存入上面6个集合
     */
    public void UpdateAllData() {//为所有数据附初值，并且更新数据

        DBcontroller.Ceshi();//测试数据库连接是否成功
        /**
         * 以下是赋值语句
         */
        WellSet = (control.getFile("Well"));//《井》数据查询集合
        ValveSet = (control.getFile("Valve"));//《阀组》数据查询集合
        StationSet = (control.getFile("Station"));//《集气站》数据查询集合
        CenterStationSet = (control.getFile("CenterStation"));//《中央处理厂》数据查询集合
        EquipmentSet = (control.getFile("Equipment"));//《增压设备》数据查询集合
        PipeSet = (control.getFile("Pipeline"));//《管道》数据查询集合
        CoalSeamSet = (control.getFile("CoalSeam"));//《煤层》数据查询集合

        /**
         * 将上述6张表中的序号列转为List存储，以便检查序号是否按照顺序编排；
         */
        List<Integer> WellNo = control.ReadInt(WellSet, "SequenceNumber");//井序号
        List<Integer> ValveNo = control.ReadInt(ValveSet, "SequenceNumber");//阀组序号
        List<Integer> StationNo = control.ReadInt(StationSet, "SequenceNumber");//序号
        List<Integer> CenterStationNo = control.ReadInt(CenterStationSet, "SequenceNumber");//序号
        List<Integer> EquipmentNo = control.ReadInt(EquipmentSet, "SequenceNumber");//序号
        List<Integer> PipeNo = control.ReadInt(PipeSet, "SequenceNumber");//序号
        List<Integer> CoalSeamNo = control.ReadInt(CoalSeamSet, "SequenceNumber");//序号

        /**
         * 如果行号与序号不对应就重新排序后重新录入数据
         */
        if (Checkxuhao("Well", WellNo)) {//检查《井》中序号是否正确，是否按照表格中的顺序，不正确就改正
            WellSet = (control.getFile("Well"));//重新录入
            WellNo = control.ReadInt(WellSet, "SequenceNumber");//重新录入井序号
        } else if (Checkxuhao("Valve", ValveNo)) {//同上
            ValveSet = (control.getFile("Valve"));
            ValveNo = control.ReadInt(ValveSet, "SequenceNumber");//阀组序号
        } else if (Checkxuhao("Station", StationNo)) {//同上
            StationSet = (control.getFile("Station"));
            StationNo = control.ReadInt(StationSet, "SequenceNumber");//序号
        } else if (Checkxuhao("CenterStation", CenterStationNo)) {//同上
            CenterStationSet = (control.getFile("CenterStation"));
            CenterStationNo = control.ReadInt(CenterStationSet, "SequenceNumber");//序号
        } else if (Checkxuhao("Equipment", EquipmentNo)) {//同上
            EquipmentSet = (control.getFile("Equipment"));
            EquipmentNo = control.ReadInt(EquipmentSet, "SequenceNumber");
        } else if (Checkxuhao("Pipeline", PipeNo)) {//同上
            PipeSet = (control.getFile("Pipeline"));
            PipeNo = control.ReadInt(PipeSet, "SequenceNumber");
        } else if (Checkxuhao("CoalSeam", CoalSeamNo)) {//同上
            CoalSeamSet = (control.getFile("CoalSeam"));
            CoalSeamNo = control.ReadInt(CoalSeamSet, "SequenceNumber");
        }
//        System.out.println("AllData1:数据准备完毕！");
    }

    /**
     * 数据库中数据删除行后依然会占用该序号，新加的行就会不与其行号对应，所以要重新排序 检查序号是否正确，否则改正, false:全部正确 ；
     * true:序号有改动 Checkxuhao(表的名称，序号列数据的List)
     */
    private boolean Checkxuhao(String name, List<Integer> list) {
        int num = list.size();//检查一列数据有多少行
        boolean check = false;//判断是否需要改正，
        for (int i = 0; i < num; i++) {//开始检查
            if (list.get(i) != (i + 1)) {//检查序号是否等于行数
                check = true;
                //如果序号没有按顺序来，就改正
                control.UpdateData(name, "SequenceNumber", (i + 1), "SequenceNumber", list.get(i));//如果不等于行数，就将数据重新编序号
            }
        }
        return check;
    }

}
