/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Background_functions;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 优化的界面
 *
 * @author 武浩
 */
public class Panel_Optim {

    private JPanel jpane2 = new JPanel();
    private JButton jbut1 = new JButton("功能1");
    private JButton jbut2 = new JButton("功能2");
//    private JButton jbut3 = new JButton("功能3");
//    private JButton jbut4 = new JButton("功能4");
    private JScrollPane otp = OutputTextPane.jscrollpane(10, 90, 475, 175);

    public JPanel panel() {
        jpane2.setLayout(null);
        jpane2.setSize(500, 300);
        jpane2.setBackground(Color.LIGHT_GRAY);
        jpane2.add(otp);
        jpane2.add(jbut1);
        jpane2.add(jbut2);
//        jpane2.add(jbut3);
//        jpane2.add(jbut4);
        jbut1.setBounds(50, 30, 150, 35);
        jbut1.setFont(new Font("黑体", Font.BOLD, 15));
        jbut1.addActionListener(new ButtonActionListener());
        jbut2.setBounds(290, 30, 150, 35);
        jbut2.setFont(new Font("黑体", Font.BOLD, 15));
        jbut2.addActionListener(new ButtonActionListener());

        return jpane2;
    }

    class ButtonActionListener implements ActionListener {

        Thread thread = new Thread("进程");

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jbut1) {
                Key0_Updater0.update();
            }
            if (e.getSource() == jbut2) {
                new Key1_Updater1().auxiliary_functions();
            }
        }
    }
}
