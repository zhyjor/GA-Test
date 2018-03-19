/**
 * 本类是Jfreechart树结构图的生成方法，用于Start_second类中
 */
package zhyh.Tool.Shower;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.filter.GraphDistanceFilter;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.ToolTipControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.io.GraphMLReader;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.GraphLib;
import prefuse.util.GraphicsLib;
import prefuse.util.display.DisplayLib;
import prefuse.util.display.ItemBoundsListener;
import prefuse.util.force.ForceSimulator;
import prefuse.util.io.IOLib;
import prefuse.util.ui.JForcePanel;
import prefuse.util.ui.JValueSlider;
import prefuse.util.ui.UILib;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;
import static zhyh.Tool.Shower.InterTree_Auxiliary.numlist;

/**
 * 与“InterTree_Auxiliary”类联合使用，作为显示工具，显示连接树结构以及提示信息
 */
public class InterTree extends JPanel {

    private static final String graph = "graph";
    private static final String nodes = "graph.nodes";
    private static final String edges = "graph.edges";
    private static String[] data;
    private Visualization m_vis;

    /**
     * 构造方法，
     */
    private InterTree(Graph g, String label) {

        // 新建显示数据功能类
        m_vis = new Visualization();

        // --------------------------------------------------------------------
        // 渲染器
        LabelRenderer tr = new LabelRenderer();
        tr.setRoundedCorner(0, 0);
        m_vis.setRendererFactory(new DefaultRendererFactory(tr));

        // --------------------------------------------------------------------
        // register the name with a visualization
        // adds graph to visualization and sets renderer label field
        setGraph(g, label);

        // 固定目标点
        TupleSet focusGroup = m_vis.getGroup(Visualization.FOCUS_ITEMS);
        focusGroup.addTupleSetListener(new TupleSetListener() {//监听器（匿名内部类）
            @Override
            public void tupleSetChanged(TupleSet ts, Tuple[] add, Tuple[] rem) {
                for (int i = 0; i < rem.length; ++i) {
                    ((VisualItem) rem[i]).setFixed(false);
                }
                for (int i = 0; i < add.length; ++i) {
                    ((VisualItem) add[i]).setFixed(false);
                    ((VisualItem) add[i]).setFixed(true);
                }
                if (ts.getTupleCount() == 0) {
                    ts.addTuple(rem[0]);
                    ((VisualItem) rem[0]).setFixed(false);
                }
                m_vis.run("draw");
            }
        });

        // --------------------------------------------------------------------
        // 显示用的
        int hops = 30;
        final GraphDistanceFilter filter = new GraphDistanceFilter(graph, hops);

        ColorAction fill = new ColorAction(nodes,
                VisualItem.FILLCOLOR, ColorLib.rgb(200, 200, 255));
        fill.add(VisualItem.FIXED, ColorLib.rgb(255, 100, 100));
        fill.add(VisualItem.HIGHLIGHT, ColorLib.rgb(255, 200, 125));

        ActionList draw = new ActionList();
        draw.add(filter);
        draw.add(fill);
        draw.add(new ColorAction(nodes, VisualItem.STROKECOLOR, 0));
        draw.add(new ColorAction(nodes, VisualItem.TEXTCOLOR, ColorLib.rgb(0, 0, 0)));
        draw.add(new ColorAction(edges, VisualItem.FILLCOLOR, ColorLib.gray(180)));
        draw.add(new ColorAction(edges, VisualItem.STROKECOLOR, ColorLib.gray(180)));

        ActionList animate = new ActionList(Activity.INFINITY);
        animate.add(new ForceDirectedLayout(graph));
        animate.add(fill);
        animate.add(new RepaintAction());

        // finally, we register our ActionList with the Visualization.
        // we can later execute our Actions by invoking a method on our
        // Visualization, using the name we've chosen below.
        m_vis.putAction("draw", draw);
        m_vis.putAction("layout", animate);

        m_vis.runAfter("draw", "layout");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕大小

        // --------------------------------------------------------------------
        // set up a display to show the visualization
        Display display = new Display(m_vis);
        display.setSize(screenSize.width / 2, screenSize.height - 110);//窗体尺寸
        display.pan(350, 350);//显示树的初始位置
        display.setForeground(Color.GRAY);//前景色
        display.setBackground(Color.WHITE);//背景色

        /**
         * 控制器*************************************
         */
        display.addControlListener(new ToolTipControl("tip"));

        /**
         * 数据输入***************************
         */
        display.addControlListener(new FocusControl(1));
        display.addControlListener(new DragControl());
        display.addControlListener(new PanControl());
        display.addControlListener(new ZoomControl());
        display.addControlListener(new WheelZoomControl());
        display.addControlListener(new ZoomToFitControl());
        display.addControlListener(new NeighborHighlightControl());

        // overview display
//        Display overview = new Display(vis);
//        overview.setSize(290,290);
//        overview.addItemBoundsListener(new FitOverviewListener());
        display.setForeground(Color.GRAY);
        display.setBackground(Color.WHITE);

        // --------------------------------------------------------------------        
        // launch the visualization
        // create a panel for editing force values
        ForceSimulator fsim = ((ForceDirectedLayout) animate.get(0)).getForceSimulator();
        JForcePanel fpanel = new JForcePanel(fsim);

        final JValueSlider slider = new JValueSlider("Distance", 0, hops, hops);//滑动钮
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                filter.setDistance(slider.getValue().intValue());
                m_vis.run("draw");
            }
        });
        slider.setBackground(Color.WHITE);
        slider.setPreferredSize(new Dimension(300, 30));
        slider.setMaximumSize(new Dimension(300, 30));

        Box cf = new Box(BoxLayout.Y_AXIS);
        cf.add(slider);
        cf.setBorder(BorderFactory.createTitledBorder("Connectivity Filter"));
        fpanel.add(cf);

        //fpanel.add(opanel);
        fpanel.add(Box.createVerticalGlue());

        // 内部图形界面的面积
        JSplitPane split = new JSplitPane();
        split.setLeftComponent(display);
        split.setRightComponent(fpanel);
        split.setOneTouchExpandable(true);
        split.setContinuousLayout(false);
        split.setDividerLocation(1000);

        // now we run our action numlist
        m_vis.run("draw");

        add(split);
    }

    /**
     * 用于上面构造方法中
     */
    private void setGraph(Graph g, String label) {
        // update labeling
        DefaultRendererFactory drf = (DefaultRendererFactory) m_vis.getRendererFactory();
        ((LabelRenderer) drf.getDefaultRenderer()).setTextField(label);

        // update graph
        m_vis.removeGroup(graph);
        VisualGraph vg = m_vis.addGraph(graph, g);
        m_vis.setValue(edges, null, VisualItem.INTERACTIVE, Boolean.FALSE);
        VisualItem f = (VisualItem) vg.getNode(0);
        m_vis.getGroup(Visualization.FOCUS_ITEMS).setTuple(f);
        f.setFixed(false);
    }

    /**
     * 自己编的数据输入方法，（输入全部点的名称，输入各个点想显示的tip内容） 构建树结构
     *
     */
    private static Graph graph(String[] name, String[] Tip) {
        InterTree.data = Tip;
        Graph graph = new Graph();
        graph.addColumn("label", String.class);
        graph.addColumn("id", int.class);
        graph.addColumn("tip", String.class);

        Node node = null;
        for (String d : name) {
            node = graph.addNode();
            node.setString("label", d);
        }
        List list1 = numlist("StartPointName");//管道起点
        List list2 = numlist("EndPointName");//管道终点
        int max = list1.size();
        for (int i = 0; i < max; i++) {//边的写入循环,输入各个点的相互连接关系
            int num1 = (int) list1.get(i);
            int num2 = (int) list2.get(i);
            graph.addEdge(num1, num2);
        }
        return graph;
    }

    //*************************************************************************
    //*************************************目标方法*****************************
    /**
     * 显示功能
     */
    public static void xianshi(String[] name, String[] tips) {
        UILib.setPlatformLookAndFeel();//改变旁边功能滑钮的视觉效果

        // create graphview
        String datafile = null;
        String label = "label";

        JFrame frame = demo(graph(name, tips), label);//**输入数据的地方*****************************************************

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//设置窗体关闭
    }

    private static JFrame demo() {//默认输入值为空
        return demo((String) null, "label");
    }

    /**
     * *******************************************这里输入读入表数据的路径，读入File类***********************************************
     */
    private static JFrame demo(String datafile, String label) {//输入值为数据表的路径
        Graph g = null;
        if (datafile == null) {//如果无输入，默认显示15*15的格子
            g = GraphLib.getGrid(15, 15);//数据，15行，15列
            label = "label";
        } else {
            try {
                g = new GraphMLReader().readGraph(datafile);
            } catch (Exception e) {
                System.exit(1);
            }
        }
        return demo(g, label);
    }

    /**
     * *******************************************上面输入读入表数据的路径，读入File类***********************************************
     */
    private static JFrame demo(Graph g, String label) {//直接输入图线类，或用于上面的方法中
        final InterTree view = new InterTree(g, label);
        /**
         * 显示tip在这里************************************
         */
//        g.getNode(0).setString("tip", "djsklf djsk");
//        g.getNode(1).setString("tip", "www" + "djsk");
        int num = InterTree.data.length;
        for (int i = 0; i < num; i++) {
            g.getNode(i).setString("tip", InterTree.data[i]);
        }
        // set up menu
        JMenu dataMenu = new JMenu("Data");
        dataMenu.add(new OpenGraphAction(view));
        dataMenu.add(new GraphMenuAction("Grid", "ctrl 1", view) {
            @Override
            protected Graph getGraph() {
                return GraphLib.getGrid(15, 15);
            }
        });
        dataMenu.add(new GraphMenuAction("Clique", "ctrl 2", view) {
            @Override
            protected Graph getGraph() {
                return GraphLib.getClique(10);
            }
        });
        dataMenu.add(new GraphMenuAction("Honeycomb", "ctrl 3", view) {
            @Override
            protected Graph getGraph() {
                return GraphLib.getHoneycomb(5);
            }
        });
        dataMenu.add(new GraphMenuAction("Balanced Tree", "ctrl 4", view) {
            @Override
            protected Graph getGraph() {
                return GraphLib.getBalancedTree(3, 5);
            }
        });
        dataMenu.add(new GraphMenuAction("Diamond Tree", "ctrl 5", view) {
            @Override
            protected Graph getGraph() {
                return GraphLib.getDiamondTree(3, 3, 3);
            }
        });
        JMenuBar menubar = new JMenuBar();
        menubar.add(dataMenu);

        // launch window
        JFrame frame = new JFrame("煤层气田管网结构示意图");
        frame.setJMenuBar(menubar);
        frame.setContentPane(view);
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                view.m_vis.run("layout");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                view.m_vis.cancel("layout");
            }
        });

        return frame;
    }

    // ------------------------------------------------------------------------
    /**
     * Swing menu action that loads a graph into the graph viewer.
     * 菜单内部抽象类，将图像输入图像浏览器
     */
    private abstract static class GraphMenuAction extends AbstractAction {//内部抽象类

        private InterTree m_view;

        public GraphMenuAction(String name, String accel, InterTree view) {
            m_view = view;
            this.putValue(AbstractAction.NAME, name);
            this.putValue(AbstractAction.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(accel));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            m_view.setGraph(getGraph(), "label");
        }

        protected abstract Graph getGraph();
    }

    /**
     * 内部类，开启图线动作***********************************************
     */
    private static class OpenGraphAction extends AbstractAction {

        private InterTree m_view;

        public OpenGraphAction(InterTree view) {
            m_view = view;
            this.putValue(AbstractAction.NAME, "Open File...");
            this.putValue(AbstractAction.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke("ctrl O"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Graph g = IOLib.getGraphFile(m_view);
            if (g == null) {
                return;
            }
            String label = getLabel(m_view, g);
            if (label != null) {
                m_view.setGraph(g, label);
            }
        }

        public static String getLabel(Component c, Graph g) {
            // get the column names
            Table t = g.getNodeTable();
            int cc = t.getColumnCount();
            String[] names = new String[cc];
            for (int i = 0; i < cc; ++i) {
                names[i] = t.getColumnName(i);
            }

            // where to store the result
            final String[] label = new String[1];

            // -- build the dialog -----
            // we need to get the enclosing frame first
            while (c != null && !(c instanceof JFrame)) {
                c = c.getParent();
            }
            final JDialog dialog = new JDialog(
                    (JFrame) c, "Choose Label Field", true);

            // create the ok/cancel buttons
            final JButton ok = new JButton("OK");
            ok.setEnabled(false);
            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.setVisible(false);
                }
            });
            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    label[0] = null;
                    dialog.setVisible(false);
                }
            });

            // build the selection numlist
            final JList list = new JList(names);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.getSelectionModel().addListSelectionListener(
                    new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                            int sel = list.getSelectedIndex();
                            if (sel >= 0) {
                                ok.setEnabled(true);
                                label[0] = (String) list.getModel().getElementAt(sel);
                            } else {
                                ok.setEnabled(false);
                                label[0] = null;
                            }
                        }
                    });
            JScrollPane scrollList = new JScrollPane(list);

            JLabel title = new JLabel("Choose a field to use for node labels:");

            // layout the buttons
            Box bbox = new Box(BoxLayout.X_AXIS);
            bbox.add(Box.createHorizontalStrut(5));
            bbox.add(Box.createHorizontalGlue());
            bbox.add(ok);
            bbox.add(Box.createHorizontalStrut(5));
            bbox.add(cancel);
            bbox.add(Box.createHorizontalStrut(5));

            // put everything into a panel
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(title, BorderLayout.NORTH);
            panel.add(scrollList, BorderLayout.CENTER);
            panel.add(bbox, BorderLayout.SOUTH);
            panel.setBorder(BorderFactory.createEmptyBorder(5, 2, 2, 2));

            // show the dialog
            dialog.setContentPane(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(c);
            dialog.setVisible(true);
            dialog.dispose();

            // return the label field selection
            return label[0];
        }
    }

    /**
     ************ 内部类，自适应监听器******************************
     */
    private static class FitOverviewListener implements ItemBoundsListener {

        private Rectangle2D m_bounds = new Rectangle2D.Double();
        private Rectangle2D m_temp = new Rectangle2D.Double();
        private double m_d = 15;

        @Override
        public void itemBoundsChanged(Display d) {
            d.getItemBounds(m_temp);
            GraphicsLib.expand(m_temp, 25 / d.getScale());

            double dd = m_d / d.getScale();
            double xd = Math.abs(m_temp.getMinX() - m_bounds.getMinX());
            double yd = Math.abs(m_temp.getMinY() - m_bounds.getMinY());
            double wd = Math.abs(m_temp.getWidth() - m_bounds.getWidth());
            double hd = Math.abs(m_temp.getHeight() - m_bounds.getHeight());
            if (xd > dd || yd > dd || wd > dd || hd > dd) {
                m_bounds.setFrame(m_temp);
                DisplayLib.fitViewToBounds(d, m_bounds, 0);
            }
        }
    }

} // end of class InterTree

