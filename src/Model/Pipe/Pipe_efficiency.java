/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Pipe;

import Tool.Data_resource.DBcontroller;
import java.sql.ResultSet;
import java.util.List;

/**
 * 管道效率评价模型
 *
 * @author 武浩
 */
public class Pipe_efficiency {

    private List<Double> cv;//煤层气定容比热
    private List<Double> MQ;//质量流量
    private List<Double> P_in;//管道入口压力
    private List<Double> P_out;//管道出口压力
    private List<Double> T_in;//管道入口温度
    private List<Double> T_out;//管道出口温度
    private List<Double> rou_in;//入口气体密度
    private List<Double> rou_out;//出口密度
    private Bwrs bwrs = new Bwrs();
    private DBcontroller db = new DBcontroller();
    private ResultSet rs;

    public Pipe_efficiency() {
        rs = db.getFile("Pipeline");
        P_in = db.ReadDouble(rs, "StartP");
        P_out = db.ReadDouble(rs, "EndP");
        T_in = db.ReadDouble(rs, "StartT");
        T_out = db.ReadDouble(rs, "EndT");

    }


}
