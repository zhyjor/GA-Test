/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Background_functions;

import Tool.Data_resource.DBcontroller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 评价界面
 *
 * @author 武浩
 */
public class Panel_Estim {

    private JSplitPane jpane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    static private WritableWorkbook wwb;
    static private WritableSheet ws;
    static private Workbook wb;
    static private Sheet sheet;
    static private jxl.Cell[] cell1;//名称
    static private jxl.Cell[] cell2;//选项

    static private boolean save = true;
    static private boolean delete = true;
    static private boolean sure = false;

    private JLabel jlab1 = new JLabel("输入名称：");//标签1=>文本框1
    private JLabel jlab2 = new JLabel("显示选项：");//标签2=>文本框2
    private JLabel jlab3 = new JLabel("百分比：");//标签3=>文本框3

    private JTextField jtf1 = new JTextField(300);//文本框1=>标签1
    private JTextField jtf2 = new JTextField(300);//文本框2=>标签2

    public JSplitPane panel() throws IOException, SQLException, ClassNotFoundException {

        jpane3.setLayout(null);
        jpane3.setSize(500, 300);
        jpane3.setBackground(Color.LIGHT_GRAY);
        LeftPanel lp = new LeftPanel();
        lp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        RightPanel rp = new RightPanel();
        lp.setBounds(0, 0, 250, 300);
        rp.setBounds(250, 0, 250, 300);
        jpane3.add(lp);
        jpane3.add(rp);

        jpane3.add(jlab1);
        jpane3.add(jlab2);
        jpane3.add(jlab3);
        jpane3.add(jtf1);
        jpane3.add(jtf2);

        return jpane3;
    }

    class Button implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

}

/**
 * ****************************************************************上面是主面板*******************************************************************
 */
class RightPanel extends JPanel {

    private JButton jbut1 = new JButton("确定");
    private JButton jbut2 = new JButton("重置");
    private JButton jbut3 = new JButton("评价");
    private JLabel jl1 = new JLabel("单井影响度评价");
    private JLabel jl2 = new JLabel("井名称>>");
    private JLabel jl3 = new JLabel("地区>>");
    private JLabel jl4 = new JLabel("标签>>");

    private JTextField jtf = new JTextField("影响度评价图");
    private DBcontroller db = new DBcontroller();
    private ResultSet rs = db.getFile("Well");
    private List<String> welllist = db.ReadString(rs, "Name");
    private List<String> regionlist = db.ReadString(rs, "Region");
    private Vector<String> allwellvector = new Vector();
    private Set<String> regionset = new HashSet();
    private JTextArea ta;
    private JCB jcb1;
    private JCB jcb2;
    private ListPane lp;
    private Set<String> well_estim;

    public RightPanel() {
        well_estim = new HashSet();
        this.setLayout(null);
        this.setBackground(Color.white);
        ta = new JTextArea();
        lp = new ListPane(ta);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lp);
        int num = welllist.size();
        regionset.add("全部地区");
        for (int i = 0; i < num; i++) {
            allwellvector.add(welllist.get(i));
            regionset.add(regionlist.get(i));
        }
        jcb2 = new JCB();
        for (String o : regionset) {
            jcb2.addItem(o);
        }
        jcb1 = new JCB(allwellvector);
        jl1.setFont(new Font("黑体", Font.BOLD, 20));
        jl2.setFont(new Font("黑体", Font.BOLD, 15));
        jl3.setFont(new Font("黑体", Font.BOLD, 15));
        jl4.setFont(new Font("黑体", Font.BOLD, 15));
        jl1.setBounds(50, 5, 150, 30);
        jl2.setBounds(10, 47, 100, 30);
        jl3.setBounds(10, 74, 100, 30);
        jl4.setBounds(10, 148, 100, 30);
        jbut1.setBounds(10, 113, 72, 25);
        jbut2.setBounds(85, 113, 72, 25);
        jbut3.setBounds(160, 113, 72, 25);
        jbut1.addActionListener(new ButtonListener());
        jbut2.addActionListener(new ButtonListener());
        jbut3.addActionListener(new ButtonListener());
        jtf.setBounds(80, 149, 150, 30);
        jtf.setFont(new Font("黑体", Font.BOLD, 14));
        add(jcb1);
        add(jcb2);
        add(jl1);
        add(jl2);
        add(jl3);
        add(jl4);
        add(jbut1);
        add(jbut2);
        add(jtf);
        add(jbut3);

    }

    class ListPane extends JScrollPane {

        int x = 10;
        int y = 180;
        int h = 85;
        int w = 230;

        public ListPane(JTextArea j) {
            super(j, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            this.setBounds(x, y, w, h);
            this.setBackground(Color.WHITE);
        }

    }

    class JCB extends JComboBox {

        int x = 80;
        int y = 50;
        int jiange = 3;
        int h = 25;
        int w = 150;

        JCB(Vector v) {
            super(v);
            this.setBounds(x, y, w, h);
            this.setMaximumRowCount(10);
        }

        JCB() {
            super();
            this.setBounds(x, y + h + jiange, w, h);
            this.setMaximumRowCount(10);
        }
    }

    class ButtonListener implements ActionListener {

        boolean sure = true;//防止重复启动线程
        Thread th;

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == jbut1) {//确定键
                String wellname = (String) jcb1.getSelectedItem();
                well_estim.add(wellname);
                ta.append("下面将要评价井：\n" + "[");
                for (String o : well_estim) {
                    ta.append("  " + o + "  ");
                }
                ta.append("]" + "\n");
                ta.append("显示影响地区：" + jcb2.getSelectedItem() + "\n");
                ta.append("图的标签为：《" + jtf.getText() + "》\n");
                ta.append("*******************************\n");
            }
            if (e.getSource() == jbut2) {//重置键
                ta.setText("");
                well_estim = new HashSet<String>();
            }
            if (e.getSource() == jbut3 && sure) {//评价键
                sure = false;
                System.out.println("开始评价计算.....");
                List<String> temp = new ArrayList();
                for (String o : well_estim) {
                    temp.add(o);
                }
                th = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            if (((String) jcb2.getSelectedItem()).equals("全部地区")) {
                                new Key6_Estimater2().estimate(temp, "");
                            } else {
                                new Key6_Estimater2().estimate(temp, (String) jcb2.getSelectedItem());
                            }
                            sure = true;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(RightPanel.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            sure = true;
                        }
                    }
                });
                th.start();
            }
        }
    }

}

/**
 * ******************************************上面是右边半个面板的*****************************************************************
 */
/**
 * **************************************************下面是左边半个面板***********************************************************
 */
class LeftPanel extends JPanel {

    private List<String> title;
    private Set<String> canshu;
    static private int clicknum = 0;//点击列表框次数
    private static int paranum = 0;//选中的参数个数
    private String region;
    private String tablename;
    private JLabel lab1 = new JLabel("井网评价");
    private JLabel lab2 = new JLabel("参数表>>");
    private JLabel lab3 = new JLabel("参数项>>");
    private JLabel lab4 = new JLabel("所属地区>>");

    private JComboBox jcb1;
    private JComboBox jcb2;
    private JTextField jtf = new JTextField("输入标签");
    private JList fruitList;
    private JButton resetButton;
    private JButton showButton;
    private JButton sureButton;
    private JTextArea resultTextArea;

    private Vector<String> vector_Tablelist;
    private Vector<String> vector_Region;
    private Vector<String> first_vector_Region;

    private WritableWorkbook wwb;
    private WritableSheet ws;
    private Workbook wb;
    private Sheet sheet;
    private DBcontroller db = new DBcontroller();
    private Connection co;
    private Statement statement;

    /**
     * Create a Graphical User Interface.
     */
    public LeftPanel() throws IOException, SQLException, ClassNotFoundException {
        title = new ArrayList<String>();
        canshu = new HashSet<String>();
        setBackground(Color.white);
        TablelistVector();

        jcb1 = new JComboBox(vector_Tablelist);
        jcb1.setBounds(94, 37, 140, 25);
        jcb1.setMaximumRowCount(10);
        jcb1.addItemListener(new JCBListener());
        add(jcb1);
        jcb2 = new JComboBox(first_vector_Region);
        jcb2.setBounds(94, 65, 140, 25);
        jcb2.setMaximumRowCount(10);
        jcb2.addItemListener(new JCBListener());
        add(jcb2);
        lab1.setBounds(80, 5, 100, 30);
        lab1.setFont(new Font("黑体", Font.BOLD, 20));
        lab2.setBounds(18, 36, 100, 30);
        lab2.setFont(new Font("黑体", Font.BOLD, 15));
        lab3.setBounds(18, 108, 100, 30);
        lab3.setFont(new Font("黑体", Font.BOLD, 15));
        lab4.setBounds(10, 62, 100, 30);
        lab4.setFont(new Font("黑体", Font.BOLD, 15));
// Create the components   
        fruitList = new JList(vector_Tablelist);
        resultTextArea = new JTextArea(6, 15);
        resetButton = new JButton("重置");
        showButton = new JButton("显示");
        sureButton = new JButton("确定");
        resetButton.setSize(100, 25);
        resetButton.addActionListener(new ResetButtonListener());
        showButton.setSize(100, 25);
        showButton.addActionListener(new ShowButtonListener());
        sureButton.setSize(100, 25);
        sureButton.addActionListener(new SureButtonListener());

        // Customize the component properties   
        fruitList.setVisibleRowCount(8);
        fruitList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fruitList.setFixedCellHeight(15);
        fruitList.setFixedCellWidth(150);
        resultTextArea.setFont(new Font("黑体", Font.BOLD, 10));

        // Register a listener for the list component   
        fruitList.setListData(firstTableVector());
        // Register a listener for the button component   
        resetButton.addActionListener(new ResetButtonListener());

        // Add components to the container   
        JPanel listPanel = new JPanel();

        listPanel.setBackground(Color.LIGHT_GRAY);
        JScrollPane fuit = new JScrollPane(fruitList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listPanel.add(fuit);

        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setBackground(Color.white);
        JScrollPane text = new JScrollPane(resultTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textAreaPanel.add(text);
        textAreaPanel.setLayout(null);
        text.setBounds(0, 0, 140, 60);
        setLayout(new BorderLayout());
        listPanel.setLayout(null);
        listPanel.setBounds(94, 94, 140, 65);
        fuit.setBounds(0, 0, 140, 65);

        resetButton.setBounds(10, 197, 70, 25);
        showButton.setBounds(10, 231, 70, 25);
        sureButton.setBounds(10, 164, 70, 25);
        jtf.setBounds(94, 164, 140, 25);
        jtf.setFont(new Font("黑体", Font.BOLD, 11));
        textAreaPanel.setBounds(94, 197, 140, 60);

        this.setLayout(null);
        add(listPanel);
        add(resetButton);
        add(showButton);
        add(sureButton);
        add(textAreaPanel);
        add(lab1);
        add(lab2);
        add(lab3);
        add(lab4);
        add(jtf);

    }

    private List<String> readDBtablelist() throws SQLException, ClassNotFoundException {
        co = db.getConnection();
        statement = co.createStatement();
        List<String> list = new ArrayList<String>();
        try {
            DatabaseMetaData dmd = (DatabaseMetaData) co.getMetaData();
            ResultSet rs = dmd.getTables(null, null, "%", null);
            while (rs.next()) {
                list.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    /*  
     * Initializes the vector.  
     */

    private void TablelistVector() throws IOException, SQLException, ClassNotFoundException {
        vector_Tablelist = new Vector();
        List<String> namelist = readDBtablelist();
        ResultSet rs = db.getFile(namelist.get(0));
        ResultSetMetaData ramd;
        Vector temp = new Vector();
        first_vector_Region = new Vector();
        List<String> region = db.ReadString(rs, "Region");
        int hangshu = region.size();
        Set<String> set = new HashSet();//去重用的
        for (int i = 0; i < hangshu; i++) {
            set.add(region.get(i));
        }
        first_vector_Region.add("全部地区");
        first_vector_Region.addAll(set);

        int num = namelist.size();
        for (int i = 0; i < num; i++) {
            vector_Tablelist.add(namelist.get(i));
        }
    }

    private Vector firstTableVector() {
        String con = (String) jcb1.getSelectedItem();
        ResultSet rs = db.getFile(con);
        ResultSetMetaData ramd;
        Vector temp = new Vector();
        try {
            ramd = rs.getMetaData();
            int num = ramd.getColumnCount();
            for (int i = 0; i < num; i++) {
                temp.add(ramd.getColumnName(i + 1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(LeftPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

    class JCBListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            try {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getSource() == jcb1) {
                        String con = (String) jcb1.getSelectedItem();
                        ResultSet rs = db.getFile(con);
                        ResultSetMetaData ramd;
                        List<String> region = db.ReadString(rs, "Region");
                        int hangshu = region.size();
                        Set<String> set = new HashSet();//去重用的
                        for (int i = 0; i < hangshu; i++) {
                            set.add(region.get(i));
                        }
                        vector_Region = new Vector();
                        vector_Region.add("全部地区");
                        vector_Region.addAll(set);
                        jcb2.removeAllItems();
                        for (String o : vector_Region) {
                            jcb2.addItem(o);
                        }
                        ramd = rs.getMetaData();
                        int num = ramd.getColumnCount();
                        Vector temp = new Vector();
                        for (int i = 0; i < num; i++) {
                            temp.add(ramd.getColumnName(i + 1));
                        }
                        fruitList.setListData(temp);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(LeftPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * This inner class handles button events.
     */
    class ResetButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            resultTextArea.setText("");
            paranum = 0;
            clicknum = 0;
            canshu = new HashSet<String>();
            title = new ArrayList<String>();
        }
    }

    class ShowButtonListener implements ActionListener {

        Thread th;

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(tablename);
            System.out.println(canshu);
            System.out.println(title);
            System.out.println(region);
            int num = title.size();
            String name[] = new String[num];
            String[] para = new String[num];
            Object[] ob = canshu.toArray();
            double[] rate = new double[num];
            for (int i = 0; i < num; i++) {
                name[i] = title.get(i);
                para[i] = (String) ob[i];
                rate[i] = 100.0;
            }
            th = new Thread(new Runnable() {
                Key5_Shower key = new Key5_Shower(name);

                @Override
                public void run() {
                    key.dataRescource(tablename, para, rate);
                    if ("全部地区".equals(region)) {
                        key.estimate_show("");
                    } else {
                        key.estimate_show(region);
                    }
                }
            });
            th.start();
        }
    }

    class SureButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String temp = jtf.getText();//标签
            String con = (String) fruitList.getSelectedValue();//列表框选项
            canshu.add(con);//选定的参数
            clicknum++;//单机次数
            paranum = canshu.size();//已选中的参数个数
            if (clicknum == paranum) {//选中新选项
                title.add(temp);//添加标签
                region = (String) jcb2.getSelectedItem();//读取地区选项
                tablename = (String) jcb1.getSelectedItem();//数据表名
                clicknum = paranum;
                resultTextArea.append("\n要评价的项目为：\n《" + tablename + "》\n=>\"" + region + "\"\n=>" + "参数：[");
                for (String o : canshu) {
                    resultTextArea.append("  " + o + " ");
                }
                resultTextArea.append("]\n");
                resultTextArea.append("*******************************************\n");
            } else {
                resultTextArea.append("已经选择了该项！");
            }

        }
    }

}
