/*
 * 该包主要包括了从数据库读取数据，将数据写入数据库的方法等；增删查改
 */
package Tool.Data_resource;

import Data.InputandClassify.AllData1;
import static Data.InputandClassify.AllData1.DBPASS;
import static Data.InputandClassify.AllData1.DBURL;
import static Data.InputandClassify.AllData1.DBUSER;
import com.mysql.jdbc.Connection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 数据库的操作方法集合： 一、Dubiao()/DubiaoNum(),用于读取数据库的数据，返回数据的列表；
 * 二、UpdateData()用于数据库更新修改数据; 三、ReadString()、ReadDouble()查询表，返回不同类型的list
 * 四、InsertData()向数据库插入新数据； 五、GetDate()转换String日期为TimeStamp； 六、UpdateDate()更新日期；
 * 七、RemoveData()删除某一行数据； 八、UpdateData()更新数据；
 *
 * @author 武浩
 */
public class DBcontroller {

    /**
     * 初始化数据库地址
     */
    static public void init() {
        File file = new File("默认.txt");
        try {
            InputStream ip = new FileInputStream(file);
            int num = (int) file.length();
            byte[] b = new byte[num];
            for (int i = 0; i < num; i++) {
                b[i] = (byte) ip.read();
            }
            ip.close();
            String content = new String(b);
            String[] str = content.split("\r\n");
            AllData1.DBURL = str[0];
            AllData1.DBUSER = str[1];
            AllData1.DBPASS = str[2];
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AllData1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AllData1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DBcontroller() {
        init();
    }

    /**
     * 测试连接数据库是否成功的方法
     *
     * @return
     */
    static public boolean Ceshi() {                    //测试数据库连接是否成功
        init();
        Connection conn = null;
        boolean link = true;
        try {
            Class.forName(AllData1.DBDRIVER);
        } catch (ClassNotFoundException ex) {
            System.out.println("数据库驱动没找到！");
        }
        try {
            conn = (Connection) DriverManager.getConnection(AllData1.DBURL, AllData1.DBUSER, AllData1.DBPASS);
        } catch (SQLException ex) {
            System.out.println("没有找到该名称的数据库（或者网络不好）！");
        }
        if (conn != null) {
            System.out.print("数据库连接成功！");
            link = true;
        } else {
            System.out.print("数据库连接失败！");
            link = false;
        }
        return link;
    }

    /**
     * 连接数据库的方法，不单独使用,用于getFile()
     *
     * @return
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Class.forName(AllData1.DBDRIVER);                     //加载数据库驱动
        conn = (Connection) DriverManager.getConnection(AllData1.DBURL, AllData1.DBUSER, AllData1.DBPASS);//
        return conn;
    }

    /**
     * 访问数据库中表的方法，将表中的数据转化为结果集合返回，用于Dubiao（）、DubiaoNum()方法中,
     *
     * @param biaoname
     * @return
     */
    public ResultSet getFile(String biaoname) {
        ResultSet rest = null;
        java.sql.Connection conn = null;
        try {
            conn = getConnection();
        } catch (SQLException ex) {
            System.out.println("没有发现该名称的数据库！");
        } catch (ClassNotFoundException ex) {
            System.out.println("数据库驱动没找到！");
        }
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement("select*from " + biaoname);
            rest = statement.executeQuery();
        } catch (SQLException ex) {
            System.out.println("无法执行搜索该名称的表！");
            System.out.println("原因可能是表格名字输入有误或数据库连接终断！");
        }
        return rest;

    }
//
//    public static void main(String[] args) {
//        Ceshi();
//        DBcontroller db = new DBcontroller();
//        System.out.println(new Transfer().ReadString(db.getFile("Pipeline"), "Time"));
//    }

    /**
     * 读取数据库中的一列数据，将其转化为List<String>
     * DubiaoString（表名称，列名称）
     *
     * @param liename
     * @return
     */
    public List<String> ReadString(ResultSet set, String liename) {
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

    public List<Integer> ReadInt(ResultSet set, String liename) {
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
     * 读取数据库中的一列数据，将其转化为List<Double>
     * DubiaoDouble（表名称，列名称）
     *
     * @param liename
     * @return
     *
     */
    public List<Double> ReadDouble(ResultSet set, String liename) {
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
     * 修改数据库中的数据的方法，为对表中某一个数据进行修改 UpdateData （表名字，要改哪一列，要改为什么值，以哪一列为纵坐标轴，纵座标）
     */
    public void UpdateData(String biaoname, String ziduan, String value, String tiaojianname, String tiaojianzhi) {

        try {

            java.sql.Connection conn = null;
            try {
                conn = getConnection(); //连接数据库
            } catch (SQLException ex) {
                System.out.println("数据库连接失败！");
            } catch (ClassNotFoundException ex) {
                System.out.println("没有数据库驱动！");
            }
            Timestamp s = new Timestamp(System.currentTimeMillis());//获取系统Time
            PreparedStatement stmt = null;
            try {
                stmt = conn.prepareStatement("UPDATE " + biaoname + " SET " + ziduan + "= ?" + " WHERE " + tiaojianname + "= ?"); //实例化Statement
            } catch (SQLException ex) {
                System.out.println("输入的要修改的表名字、列名或者条件值有误！");
            }
            PreparedStatement updatetime = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement;
            stmt.setString(1, value);//设置第一个？处的值
            updatetime.setTimestamp(1, s);//设置Time列的值
            stmt.setString(2, tiaojianzhi);//设置查找条件值
            updatetime.setString(2, tiaojianzhi);//设置查找条件值
            updatetime.executeUpdate();//执行更新
            stmt.executeUpdate();//执行更新
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        }

    }

    public void UpdateData(String biaoname, String ziduan, String value, String tiaojianname, int tiaojianzhi) {
        try {
            java.sql.Connection conn = getConnection();//连接数据库
            Timestamp s = new Timestamp(System.currentTimeMillis());//获取系统Time
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + biaoname + " SET " + ziduan + "= ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement
            PreparedStatement updatetime = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement;
            stmt.setString(1, value);//设置第一个？处的值
            updatetime.setTimestamp(1, s);//设置Time列的值
            stmt.setInt(2, tiaojianzhi);//设置查找条件值
            updatetime.setInt(2, tiaojianzhi);//设置查找条件值
            updatetime.executeUpdate();//执行更新
            stmt.executeUpdate();//执行更新
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    public void UpdateData(String biaoname, String ziduan, String value, String tiaojianname, double tiaojianzhi) {
        try {
            java.sql.Connection conn = getConnection();//连接数据库
            Timestamp s = new Timestamp(System.currentTimeMillis());//获取系统Time
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + biaoname + " SET " + ziduan + "= ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement
            PreparedStatement updatetime = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement;
            stmt.setString(1, value);//设置第一个？处的值
            updatetime.setTimestamp(1, s);//设置Time列的值
            stmt.setDouble(2, tiaojianzhi);//设置查找条件值
            updatetime.setDouble(2, tiaojianzhi);//设置查找条件值
            updatetime.executeUpdate();//执行更新
            stmt.executeUpdate();//执行更新
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    public void UpdateData(String biaoname, String ziduan, double value, String tiaojianname, String tiaojianzhi) {
        try {
            java.sql.Connection conn = getConnection();//连接数据库
            Timestamp s = new Timestamp(System.currentTimeMillis());//获取系统Time
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + biaoname + " SET " + ziduan + "= ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement
            PreparedStatement updatetime = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement;
            stmt.setDouble(1, value);//设置第一个？处的值
            updatetime.setTimestamp(1, s);//设置Time列的值
            stmt.setString(2, tiaojianzhi);//设置查找条件值
            updatetime.setString(2, tiaojianzhi);//设置查找条件值
            updatetime.executeUpdate();//执行更新
            stmt.executeUpdate();//执行更新
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    public void UpdateData(String biaoname, String ziduan, double value, String tiaojianname, int tiaojianzhi) {
        try {
            java.sql.Connection conn = getConnection();//连接数据库
            Timestamp s = new Timestamp(System.currentTimeMillis());//获取系统Time
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + biaoname + " SET " + ziduan + "= ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement
            PreparedStatement updatetime = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement;
            stmt.setDouble(1, value);//设置第一个？处的值
            updatetime.setTimestamp(1, s);//设置Time列的值
            stmt.setInt(2, tiaojianzhi);//设置查找条件值
            updatetime.setInt(2, tiaojianzhi);//设置查找条件值
            updatetime.executeUpdate();//执行更新
            stmt.executeUpdate();//执行更新
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    public void UpdateData(String biaoname, String ziduan, double value, String tiaojianname, double tiaojianzhi) {
        try {
            java.sql.Connection conn = getConnection();//连接数据库
            Timestamp s = new Timestamp(System.currentTimeMillis());//获取系统Time
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + biaoname + " SET " + ziduan + "= ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement
            PreparedStatement updatetime = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement;
            stmt.setDouble(1, value);//设置第一个？处的值
            updatetime.setTimestamp(1, s);//设置Time列的值
            stmt.setDouble(2, tiaojianzhi);//设置查找条件值
            updatetime.setDouble(2, tiaojianzhi);//设置查找条件值
            updatetime.executeUpdate();//执行更新
            stmt.executeUpdate();//执行更新
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    public void UpdateData(String biaoname, String ziduan, int value, String tiaojianname, String tiaojianzhi) {
        try {
            java.sql.Connection conn = getConnection();//连接数据库
            Timestamp s = new Timestamp(System.currentTimeMillis());//获取系统Time
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + biaoname + " SET " + ziduan + "= ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement
            PreparedStatement updatetime = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement;
            stmt.setInt(1, value);//设置第一个？处的值
            updatetime.setTimestamp(1, s);//设置Time列的值
            stmt.setString(2, tiaojianzhi);//设置查找条件值
            updatetime.setString(2, tiaojianzhi);//设置查找条件值
            updatetime.executeUpdate();//执行更新
            stmt.executeUpdate();//执行更新
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    public void UpdateData(String biaoname, String ziduan, int value, String tiaojianname, int tiaojianzhi) {
        try {
            java.sql.Connection conn = getConnection();//连接数据库
            Timestamp s = new Timestamp(System.currentTimeMillis());//获取系统Time
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + biaoname + " SET " + ziduan + "= ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement
            PreparedStatement updatetime = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement;
            stmt.setInt(1, value);//设置第一个？处的值
            updatetime.setTimestamp(1, s);//设置Time列的值
            stmt.setInt(2, tiaojianzhi);//设置查找条件值
            updatetime.setInt(2, tiaojianzhi);//设置查找条件值
            updatetime.executeUpdate();//执行更新
            stmt.executeUpdate();//执行更新
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    public void UpdateData(String biaoname, String ziduan, int value, String tiaojianname, double tiaojianzhi) {
        try {
            java.sql.Connection conn = getConnection();//连接数据库
            Timestamp s = new Timestamp(System.currentTimeMillis());//获取系统Time
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + biaoname + " SET " + ziduan + "= ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement
            PreparedStatement updatetime = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement;
            stmt.setInt(1, value);//设置第一个？处的值
            updatetime.setTimestamp(1, s);//设置Time列的值
            stmt.setDouble(2, tiaojianzhi);//设置查找条件值
            updatetime.setDouble(2, tiaojianzhi);//设置查找条件值
            updatetime.executeUpdate();//执行更新
            stmt.executeUpdate();//执行更新
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    /**
     * 修改数据库中的数据的方法，为对表中某一行中的参数进行修改 UpdateData （表名字，要改哪些参数，参数值，以哪一列为纵坐标轴，纵座标）
     */
    public void UpdateData(String biaoname, List ziduan, List value, String tiaojianname, String tiaojianzhi) {
        try {
            //对一行格子数据进行修改,（表名字，要改哪一列，要改的值，以哪一列为纵坐标轴，列座标）
            if (ziduan.size() != value.size()) {
                System.out.println("字段数目与值的数目不符！");
            }
            int max = ziduan.size();
            Timestamp s = new Timestamp(System.currentTimeMillis());//取得系统中的Time
            java.sql.Connection conn = getConnection();//连接数据库
            String tongpeifu = "";//设置通配符

            for (int i = 0; i < max - 1; i++) {//最后一个没有",",拿出最后一个单独赋值
                tongpeifu = tongpeifu + ziduan.get(i) + " = ?,";
            }
            tongpeifu = tongpeifu + ziduan.get(max - 1);//设置字段数个通配符 
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?," + tongpeifu + " = ?" + " WHERE " + tiaojianname + " = ?");//实例化Statement
            stmt.setObject(max + 2, tiaojianzhi);//为条件值设置通配符
            stmt.setObject(1, s);//更新Time
            for (int i = 2; i < max + 2; i++) {
                stmt.setObject(i, value.get(i - 2));//为待修改的值得通配符赋值
            }
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    public void UpdateData(String biaoname, List ziduan, List value, String tiaojianname, int tiaojianzhi) {
        try {
            //对一行格子数据进行修改,（表名字，要改哪一列，要改的值，以哪一列为纵坐标轴，列座标）
            if (ziduan.size() != value.size()) {
                System.out.println("字段数目与值的数目不符！");
            }
            int max = ziduan.size();
            Timestamp s = new Timestamp(System.currentTimeMillis());//取得系统中的Time
            java.sql.Connection conn = getConnection();//连接数据库
            String tongpeifu = "";//设置通配符

            for (int i = 0; i < max - 1; i++) {//最后一个没有",",拿出最后一个单独赋值
                tongpeifu = tongpeifu + ziduan.get(i) + " = ?,";
            }
            tongpeifu = tongpeifu + ziduan.get(max - 1);//设置字段数个通配符

            PreparedStatement stmt = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?," + tongpeifu + " = ?" + " WHERE " + tiaojianname + " = ?");//实例化Statement
            stmt.setObject(max + 2, tiaojianzhi);//为条件值设置通配符
            stmt.setObject(1, s);//更新Time
            for (int i = 2; i < max + 2; i++) {
                stmt.setObject(i, value.get(i - 2));//为待修改的值得通配符赋值
            }
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    public void UpdateData(String biaoname, List ziduan, List value, String tiaojianname, double tiaojianzhi) {
        try {
            //对一行格子数据进行修改,（表名字，要改哪一列，要改的值，以哪一列为纵坐标轴，列座标）
            if (ziduan.size() != value.size()) {
                System.out.println("字段数目与值的数目不符！");
            }
            int max = ziduan.size();
            Timestamp s = new Timestamp(System.currentTimeMillis());//取得系统中的Time
            java.sql.Connection conn = getConnection();//连接数据库
            String tongpeifu = "";//设置通配符

            for (int i = 0; i < max - 1; i++) {//最后一个没有",",拿出最后一个单独赋值
                tongpeifu = tongpeifu + ziduan.get(i) + " = ?,";
            }
            tongpeifu = tongpeifu + ziduan.get(max - 1);//设置字段数个通配符

            PreparedStatement stmt = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?," + tongpeifu + " = ?" + " WHERE " + tiaojianname + " = ?");//实例化Statement
            System.out.println("UPDATE " + biaoname + " SET Time = ?," + tongpeifu + " = ?" + " WHERE " + tiaojianname + " = ?");
            stmt.setObject(max + 2, tiaojianzhi);//为条件值设置通配符
            stmt.setObject(1, s);//更新Time
            for (int i = 2; i < max + 2; i++) {
                stmt.setObject(i, value.get(i - 2));//为待修改的值得通配符赋值
            }
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    /**
     * 修改数据库中的数据的方法，均为对表中某一列中的数据进行修改 UpdateData （表名字，要改哪一列，参数值，以哪一列为纵坐标轴，纵座标）
     */
    public void UpdateData(String biaoname, String ziduan, List value, String tiaojianname, List tiaojianzhi) {
        try {
            //对一列格子数据进行修改（表名字，要改哪一列，要改的值，以哪一列为纵坐标轴，列座标）
            if (value.size() != tiaojianzhi.size()) {
                System.out.println("字段数目与条件值数目不符，请检查输入！");
            }
            Timestamp s = new Timestamp(System.currentTimeMillis());
            java.sql.Connection conn = getConnection();//连接数据库
            int size = tiaojianzhi.size();
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + biaoname + " SET " + ziduan + "= ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement
            PreparedStatement updatetime = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?" + " WHERE " + tiaojianname + "= ?");//实例化Statement;
            for (int i = 0; i < size; i++) {
                updatetime.setTimestamp(1, s);//设置Time列的值
                stmt.setObject(1, value.get(i));
                updatetime.setObject(2, tiaojianzhi.get(i));//设置查找条件值
                stmt.setObject(2, tiaojianzhi.get(i));
                stmt.executeUpdate();
                updatetime.executeUpdate();//执行更新
            }

            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    /**
     * 向表中插入一个新数据 （表名称，列名称，插入值)
     */
    public void InsertData(String biaoname, String lieming, Object value) {
        try {
            //插入语句，
            Timestamp s = new Timestamp(System.currentTimeMillis());//取得系统中的Time
            java.sql.Connection conn = getConnection();//连接数据库
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + biaoname + " (Time," + lieming + ")" + " VALUES (?,?)");//实例化Statement
            stmt.setObject(1, s);
            stmt.setObject(2, value);
            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    /**
     * 向数据库中插入一行多值（表名称，列名，值）
     */
    public void InsertData(String biaoname, List lieming, List value) {
        try {
            //插入语句，
            if (lieming.size() != value.size()) {
                System.out.println("字段数目与值的数目不符！");
            }
            int max = lieming.size();
            Timestamp s = new Timestamp(System.currentTimeMillis());//取得系统中的Time
            java.sql.Connection conn = getConnection();//连接数据库
            String tongpeifu = "";
            String ziduan = "";
            for (int i = 0; i < max - 1; i++) {//最后一个没有","
                ziduan = ziduan + lieming.get(i) + ",";
                tongpeifu = tongpeifu + "?,";
            }
            ziduan = ziduan + lieming.get(max - 1);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + biaoname + " (Time," + ziduan + ")" + " VALUES (?," + tongpeifu + "?)");//实例化Statement 
            stmt.setObject(1, s);
            for (int i = 0; i < max; i++) {
                stmt.setObject(i + 2, value.get(i));
            }

            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    /**
     * 将String日期,转化为Date型 "yyyy-MM-dd hh:mm:ss.S"
     */
    public Timestamp GetDate(String date) {
        try {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            ts = Timestamp.valueOf(date);
            return ts;
        } catch (Exception e) {
            System.out.println("时间输入格式必须为'yyyy-MM-dd hh:mm:ss.S'");
        }
        return null;
    }

    /**
     * 输入（年，月，日，时，分，秒）转换成Timestamp型数据，可写入数据库或比较Time先后
     */
    public Timestamp GetDate(int year, int month, int day, int hour, int minute, int second) {
        String y = "" + year;
        String M = "" + month;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        if (month < 10) {
            M = "0" + month;
        }
        String d = "" + day;
        if (day < 10) {
            d = "0" + day;
        }
        String h = "" + hour;
        if (hour < 10) {
            h = "0" + hour;
        }
        String m = "" + minute;
        if (minute < 10) {
            m = "0" + minute;
        }
        String s = "" + second;
        if (minute < 10) {
            s = "0" + second;
        }
        String date = y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s + ".0";
        ts = Timestamp.valueOf(date);

        return ts;
    }

    /**
     * 更新某一项Time为最新值 默认系统最新Time ； （表名字，以哪列为纵坐标轴，纵坐标）
     */
    public void UpdateDate(String biaoname, String tiaojian, Object tiaojianzhi) {
        try {
            //更新某一项的Time为最新Time
            Timestamp s = new Timestamp(System.currentTimeMillis());
            java.sql.Connection conn = getConnection();//连接数据库
            PreparedStatement updatetime = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?" + " WHERE " + tiaojian + "= ?");//实例化Statement;
            updatetime.setTimestamp(1, s);//设置Time列的值
            updatetime.setObject(2, tiaojianzhi);//设置查找条件值         
            updatetime.executeUpdate();//执行更新
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    /**
     * 更新某一项Time为输入值 ； （表名字，以哪列为纵坐标轴，纵坐标）；
     */
    public void UpdateDate(String biaoname, String tiaojian, Object tiaojianzhi, Timestamp time) {
        try {
            //更新某一项的Time为输入Time
            Timestamp s = time;
            java.sql.Connection conn = getConnection();//连接数据库
            PreparedStatement updatetime = conn.prepareStatement("UPDATE " + biaoname + " SET Time = ?" + " WHERE " + tiaojian + "= ?");//实例化Statement;
            updatetime.setTimestamp(1, s);//设置Time列的值
            updatetime.setObject(2, tiaojianzhi);//设置查找条件值         
            updatetime.executeUpdate();//执行更新
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }

    /**
     * 删除某一行（表名字，条件，条件值）
     */
    public void RemoveData(String biaoname, String tiaojian, Object tiaojianzhi) {
        try {
            Timestamp s = new Timestamp(System.currentTimeMillis());//取得系统Time
            java.sql.Connection conn = getConnection();//连接数据库
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + biaoname + " WHERE " + tiaojian + "= ?");//实例化Statement;
            stmt.setObject(1, tiaojianzhi);//设置Time列的值
            stmt.executeUpdate();//执行更新
            conn.close();
        } catch (SQLException ex) {
            System.out.println("输入的查询或修改内容有误！");
        } catch (ClassNotFoundException ex) {
            System.out.println("没有数据库驱动！");
        }
    }
}
