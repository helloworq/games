package org.game.plane.ui;

import javax.swing.*;

public class TestConbine {
    public static void main(String[] args) {
        JFrame jf = new JFrame("测试窗口");
        jf.setSize(500, 500);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建分隔面板
        JSplitPane splitPane = new JSplitPane();

        // 设置左右两边显示的组件
        // 创建选项卡面板
        final JTabbedPane tabbedPane = new JTabbedPane();
        // 创建第 1 个选项卡（选项卡只包含 标题）
        tabbedPane.addTab("Tab01", createTextPanel("TAB 01"));

        // 创建第 2 个选项卡（选项卡包含 标题 和 图标）
        tabbedPane.addTab("Tab02", new ImageIcon("bb.jpg"), createTextPanel("TAB 02"));

        // 创建第 3 个选项卡（选项卡包含 标题、图标 和 tip提示）
        tabbedPane.addTab("Tab03", new ImageIcon("bb.jpg"), createTextPanel("TAB 03"), "This is a tab.");

        // 添加选项卡选中状态改变的监听器
        tabbedPane.addChangeListener(e -> System.out.println("当前选中的选项卡: " + tabbedPane.getSelectedIndex()));

        final JTabbedPane tabbedPane2 = new JTabbedPane();


        // 创建第 1 个选项卡（选项卡只包含 标题）
        tabbedPane2.addTab("全部", createTextPanel("TAB 01"));

        // 创建第 2 个选项卡（选项卡包含 标题 和 图标）
        tabbedPane2.addTab("队伍", new ImageIcon("bb.jpg"), createTextPanel("TAB 02"));

        // 创建第 3 个选项卡（选项卡包含 标题、图标 和 tip提示）
        tabbedPane2.addTab("悄悄话", new ImageIcon("bb.jpg"), createTextPanel("TAB 03"), "This is a tab.");


        // 添加选项卡选中状态改变的监听器
        tabbedPane2.addChangeListener(e -> System.out.println("当前选中的选项卡: " + tabbedPane.getSelectedIndex()));

        splitPane.setLeftComponent(tabbedPane);
        splitPane.setRightComponent(tabbedPane2);

        splitPane.setContinuousLayout(true);
        splitPane.setDividerLocation(300);
        splitPane.setEnabled(false);
        jf.setContentPane(splitPane);
        jf.setVisible(true);
    }

    /**
     * 创建一个面板，面板中心显示一个标签，用于表示某个选项卡需要显示的内容
     */
    private static JComponent createTextPanel(String text) {
        // 创建面板, 使用一个 1 行 1 列的网格布局（为了让标签的宽高自动撑满面板）
        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel(layout);

        //文本域
        JScrollPane msgScreen = new JScrollPane();//使用滑动组件包装输入框
        JTextArea textArea4MsgScreen = new JTextArea();
        textArea4MsgScreen.setLineWrap(true);
        textArea4MsgScreen.setEditable(false);
        msgScreen.setViewportView(textArea4MsgScreen);

        //输入框
        JScrollPane inputArea = new JScrollPane();//使用滑动组件包装输入框
        JTextArea textArea4InputArea = new JTextArea();
        textArea4InputArea.setLineWrap(true);
        inputArea.setViewportView(textArea4InputArea);

        //提交按钮
        JButton btn = new JButton("发送");
        btn.addActionListener(e -> {
            String content = textArea4InputArea.getText();
            textArea4MsgScreen.append(content + "\n");
            textArea4InputArea.setText("");
        });
        //清屏
        JButton btnClear = new JButton("清屏");
        btnClear.addActionListener(e -> textArea4MsgScreen.setText(""));


        // 添加标签到面板
        panel.add(msgScreen);
        panel.add(inputArea);
        panel.add(btn);
        panel.add(btnClear);

        //调整位置
        layout.putConstraint(SpringLayout.WEST, msgScreen, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, msgScreen, 5, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, msgScreen, -5, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, msgScreen, -70, SpringLayout.SOUTH, panel);

        layout.putConstraint(SpringLayout.WEST, btn, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, btn, 10, SpringLayout.SOUTH, msgScreen);

        layout.putConstraint(SpringLayout.WEST, btnClear, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, btnClear, 5, SpringLayout.SOUTH, btn);

        layout.putConstraint(SpringLayout.WEST, inputArea, 5, SpringLayout.EAST, btn);
        layout.putConstraint(SpringLayout.NORTH, inputArea, 10, SpringLayout.SOUTH, msgScreen);
        layout.putConstraint(SpringLayout.EAST, inputArea, -5, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, inputArea, -5, SpringLayout.SOUTH, panel);

        return panel;
    }
}
