/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhyh.Background_functions;

import java.io.PrintStream;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 将系统中输出内容转到该面板上
 *
 * @author 武浩
 */
public class OutputTextPane extends PrintStream {

    private JTextArea jta = null;

    private OutputTextPane(JTextArea jta) {
        super(System.out);
        this.jta = jta;
    }

    public void print(String str) {
        if (jta != null) {
            jta.append(str + "\r\n");
        }
    }

    public void println(String str) {
        if (jta != null) {
            jta.append(str + "\r\n");
        }
    }

    static public JScrollPane jscrollpane(int x, int y, int width, int height) {
        JTextArea jta = new JTextArea();
        JScrollPane jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setBounds(x, y, width, height);
        OutputTextPane ot = new OutputTextPane(jta);
        System.setOut(ot);
        return jsp;
    }

}
