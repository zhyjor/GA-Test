/*
 *设置数据库连接信息的界面
 */
package Operation_Panel;

import Background_functions.Panel_Estim;
import Background_functions.Panel_Optim;
import Tool.Data_resource.DBcontroller;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author 武浩
 */
public class Panel_for_Operation {

    private List<String> name;
    private List<String> url;
    private List<String> user;
    private List<String> password;
    static private WritableWorkbook wwb;
    static private WritableSheet ws;
    static private Workbook wb;
    static private Sheet sheet;
    static private jxl.Cell[] cell1;//名称
    static private jxl.Cell[] cell2;//URL
    static private jxl.Cell[] cell3;//USER
    static private jxl.Cell[] cell4;//Password]
    static private boolean save = true;
    static private boolean delete = true;
    static private boolean sure = false;

    /**
     * 界面
     */
    private JFrame jframe = new JFrame("数据库相关参数输入界面");//窗体
    private JTabbedPane jtab = new JTabbedPane(JTabbedPane.TOP);//多选项卡面板
    private JPanel jpane1 = new JPanel();//面板1
    private Panel_Optim jpane2 = new Panel_Optim();//面板2
    private Panel_Estim jpane3 = new Panel_Estim();//面板3
    /**
     * 组件
     */
    private JButton jbut1 = new JButton("确认");//按钮
    private JButton jbut2 = new JButton("保存");//按钮
    private JButton jbut3 = new JButton("删除");//按钮

    private JLabel jlab1 = new JLabel("   数据库地址URL：");//标签1=>文本框1
    private JLabel jlab2 = new JLabel("      数据库用户名：");//标签2=>文本框2
    private JLabel jlab3 = new JLabel("          数据库密码：");//标签3=>文本框3
    private JLabel jlab4 = new JLabel("     现有的数据库：");//标签4=>下拉列表1
    private JLabel jlab5 = new JLabel("尝试连接数据库...");//标签4=>下拉列表1

    private JTextField jtf1 = new JTextField(300);//文本框1=>标签1
    private JTextField jtf2 = new JTextField(300);//文本框2=>标签2
    private JTextField jtf3 = new JTextField(300);//文本框3=>标签3
    private JComboBox jcb1;//下拉列表1=>标签4

    public Panel_for_Operation() throws IOException, BiffException {
        input();
    }

    private void createFrame() throws IOException {

        jframe.setSize(520, 350);
        jframe.setLocation(300, 300);
        jframe.setLayout(null);//启用绝对坐标

        jtab.add("数据库设置", jpane1);
        jtab.add("优化", jpane2.panel());
        try {
            jtab.add("评价", jpane3.panel());
        } catch (SQLException ex) {
            Logger.getLogger(Panel_for_Operation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Panel_for_Operation.class.getName()).log(Level.SEVERE, null, ex);
        }
        jtab.setSize(500, 300);

        jbut1.setBounds(210, 120, 100, 25);
        jbut2.setBounds(324, 160, 60, 25);
        jbut3.setBounds(391, 160, 60, 25);
        jtf1.setBounds(150, 20, 300, 25);
        jtf2.setBounds(150, 50, 300, 25);
        jtf3.setBounds(150, 80, 300, 25);
        jtf1.setText(url.get(0));
        jtf2.setText(user.get(0));
        jtf3.setText(password.get(0));

        jpane1.setLayout(null);
        jpane1.setSize(500, 300);
        jpane1.setBackground(Color.LIGHT_GRAY);

        jcb1.setBounds(150, 160, 165, 25);
        jcb1.setMaximumRowCount(10);
        jpane1.add(jcb1);
        jlab1.setBounds(15, 20, 120, 25);
        jlab2.setBounds(15, 50, 120, 25);
        jlab3.setBounds(15, 80, 120, 25);
        jlab4.setBounds(15, 160, 120, 25);
        jlab5.setBounds(45, 225, 300, 25);
        jlab5.setForeground(Color.red);
        jpane1.add(jlab1);
        jpane1.add(jlab2);
        jpane1.add(jlab3);
        jpane1.add(jlab4);
        jpane1.add(jtf1);
        jpane1.add(jtf2);
        jpane1.add(jtf3);
        jpane1.add(jbut1);
        jpane1.add(jbut2);
        jpane1.add(jbut3);
        jpane1.add(jlab5);
        jframe.setVisible(true);
        jframe.add(jtab);
        jframe.setVisible(true);

        jbut1.addActionListener(new ButtonActions());
        jbut2.addActionListener(new ButtonActions());
        jbut3.addActionListener(new ButtonActions());
        jcb1.addItemListener(new ComboxActons());
        jcb1.setEditable(true);

        /**
         * 加入窗体事件监听
         */
        jframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                if (save && delete) {//确保：保存、删除操作结束
                    System.exit(1);
                }
            }
        });
    }

    /**
     * 按钮事件
     */
    class ButtonActions implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                if (e.getSource() == jbut1) {//确认键
                    sure = false;
                    String str1 = jtf1.getText() + "\r\n" + jtf2.getText() + "\r\n" + jtf3.getText();
                    File file = new File("默认.txt");
                    try {
                        OutputStream os = new FileOutputStream(file);
                        byte b[] = str1.getBytes();
                        for (byte o : b) {
                            os.write(o);
                        }
                        os.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Panel_for_Operation.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Panel_for_Operation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (DBcontroller.Ceshi()) {
                        jlab5.setText("连接成功！！");
                        jlab5.setForeground(Color.red);
                        sure = true;
                    } else {
                        jlab5.setText("连接失败！！");
                        jlab5.setForeground(Color.red);
                        sure = false;
                    }
                }
                if (e.getSource() == jbut2) {//保存键
                    save = false;
                    try {
                        String con = (String) jcb1.getSelectedItem();
                        if (!isExists(con)) {
                            System.out.println("新的资料！" + con);
                            name.add(con);
                            url.add(jtf1.getText());
                            user.add(jtf2.getText());
                            password.add(jtf3.getText());
                            jcb1.addItem(con);
                            wwb = Workbook.createWorkbook(new File("SaveDataBases.xls"));
                            ws = wwb.createSheet("Databases", 0);
                            int num = name.size();
                            for (int i = 0; i < num; i++) {
                                ws.addCell(new jxl.write.Label(0, i, name.get(i)));
                                ws.addCell(new jxl.write.Label(1, i, url.get(i)));
                                ws.addCell(new jxl.write.Label(2, i, user.get(i)));
                                ws.addCell(new jxl.write.Label(3, i, password.get(i)));
                            }
                            wwb.write();
                            wwb.close();

                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Panel_for_Operation.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (WriteException ex) {
                        Logger.getLogger(Panel_for_Operation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    save = true;
                }

                if (e.getSource() == jbut3) {//删除键
                    delete = false;
                    String con = (String) jcb1.getSelectedItem();
                    try {
                        int num = name.size();
                        for (int i = 0; i < num; i++) {
                            if (con.equals(name.get(i))) {
                                name.remove(i);
                                url.remove(i);
                                user.remove(i);
                                password.remove(i);
                                jcb1.removeItem(con);
                                break;
                            }
                        }
                        wwb = Workbook.createWorkbook(new File("SaveDataBases.xls"));
                        ws = wwb.createSheet("Databases", 0);
                        num = name.size();
                        for (int i = 0; i < num; i++) {
                            ws.addCell(new jxl.write.Label(0, i, name.get(i)));
                            ws.addCell(new jxl.write.Label(1, i, url.get(i)));
                            ws.addCell(new jxl.write.Label(2, i, user.get(i)));
                            ws.addCell(new jxl.write.Label(3, i, password.get(i)));
                        }
                        wwb.write();
                        wwb.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Panel_for_Operation.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (WriteException ex) {
                        Logger.getLogger(Panel_for_Operation.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    delete = true;
                }
            }
        }
    }

    /**
     * 下拉列表事件
     */
    class ComboxActons implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            String con = (String) jcb1.getSelectedItem();
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (isExists(con)) {
                    int num = name.size();
                    for (int i = 0; i < num; i++) {
                        if (name.get(i).equals(con)) {
                            try {
                                jtf1.setText(url.get(i));
                                jtf2.setText(user.get(i));
                                jtf3.setText(password.get(i));
                            } catch (Exception ex) {
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 判断下拉列表中是否存在输入值
     */
    private boolean isExists(String item) {
        boolean f = false;
        int num = jcb1.getItemCount();
        for (int i = 0; i < num; i++) {
            if (item.equals(jcb1.getItemAt(i))) {
                f = true;
            }
        }
        return f;
    }

    /**
     * 读取存储记录
     */
    private void input() throws IOException, BiffException {
        wb = Workbook.getWorkbook(new File("SaveDataBases.xls"));
        sheet = wb.getSheet(0);

        cell1 = sheet.getColumn(0);//名称
        cell2 = sheet.getColumn(1);//URL
        cell3 = sheet.getColumn(2);//USER
        cell4 = sheet.getColumn(3);//Password]

        int num = cell1.length;
        name = new ArrayList<String>();
        url = new ArrayList<String>();
        user = new ArrayList<String>();
        password = new ArrayList<String>();

        String[] temp = new String[num];
        for (int i = 0; i < num; i++) {
            temp[i] = cell1[i].getContents();
            name.add(cell1[i].getContents());
            url.add(cell2[i].getContents());
            user.add(cell3[i].getContents());
            password.add(cell4[i].getContents());
        }
        jcb1 = new JComboBox(temp);
    }

    public static void main(String[] args) throws IOException, BiffException, WriteException, InterruptedException {
        new Panel_for_Operation().createFrame();
    }

}
