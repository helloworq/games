package org.game.plane.frame;

import org.game.plane.ImageUtil;
import org.game.plane.common.bullets.Bullet;
import org.game.plane.constans.Config;
import org.game.plane.event.KeyMointer;
import org.game.plane.log.LogServer;
import org.game.plane.common.planes.Plane;
import org.game.plane.run.Run;
import org.game.plane.source.GameSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlaneClient extends Frame {
    public static void main(String[] args) {
        new PlaneClient();
    }

    //窗体参数
    public static final int X = 400;
    public static final int Y = 400;
    public static final int deviation = 10;
    public static final int WINDOW_SPLITER = 300;
    //蛇的初始坐标
    public int StartPositionX = 200;
    public int StartPositionY = 200;
    //机身大小
    public static int PlaneSize = 20;
    public static int BulletSize = 10;
    //使用链表维护蛇身节点数据
    private static final List<Plane> planeList = new CopyOnWriteArrayList<>();
    public static List<Bullet> bulletList = new CopyOnWriteArrayList<>();
    //游戏速度
    public final static int GAME_SPEED = 20;
    private static final int PRESSED = 1;
    private static final int RELEASED = 0;

    public PlaneClient() {
        this.add(createSplitPanel());
        //实现程序运行关闭的功能
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });
        //调用键盘
        this.addKeyListener(KeyMointer.getInstance(this));
        this.setBounds(0, 0, X, Y); //设置窗体大小和位置
        this.setVisible(true); //使用该属性才能显示窗体
    }

    public void addPlane(Plane plane) {
        planeList.add(plane);
    }

    public static void addPlaneList(List<Plane> planes) {
        if (Objects.nonNull(planes) && !planes.isEmpty()) {
            planeList.forEach(e -> planes.removeIf(p -> p.getId().equals(e.getId())));
        }
        planeList.addAll(planes);
    }

    public static Plane getPlane(String id) {
        return planeList.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    private void drawPlane(Graphics g) {
        for (Plane plane : planeList) {
            handleMove(plane);
            BufferedImage image = ImageUtil.rotateImage(GameSource.get(plane.getImageId()), plane.getRotate());
            g.drawImage(image, plane.getPositionX(), plane.getPositionY(), null);
        }
    }

    private void drawBullets(Graphics g) {
        for (Bullet bullet : bulletList) {
            bullet.bulletMove();
            BufferedImage image = ImageUtil.rotateImage(GameSource.get(bullet.getImageId()), bullet.getRotate());
            g.drawImage(image, bullet.getPositionX(), bullet.getPositionY(), null);

            recyclingBullets();
        }
    }

    private void drawCircleBullte(Graphics g) {
        if (Objects.nonNull(planeList) && !planeList.isEmpty()) {
            Plane plane = planeList.get(0);
            g.drawString("测试",10,10);
            //Run.Position position = getCirclePosition();
            g.drawString(plane.getId(), plane.getPositionX(), plane.getPositionY() + 50);
            if (planeList.size()>1){
                Plane plane2 = planeList.get(1);
                //Run.Position position = getCirclePosition();
                g.drawString(plane2.getId(), plane2.getPositionX(), plane2.getPositionY() + 50);
            }
        }
    }

    double angle = 0;

    public Run.Position getCirclePosition() {
        //根据直径计算坐标(加上距圆心偏移量即为坐标)
        double length = 100.0;
        int y = (int) (Math.sin(angle * Math.PI) * length);
        int x = (int) (Math.cos(angle * Math.PI) * length);
        Run.Position position = new Run.Position(x, y);

        angle = angle < 2 ? (angle + 1.0 / 128.0) : 0.0;
        return position;
    }

    private void handleMove(Plane plane) {
        if (KeyMointer.getKeyMap("w" + plane.getId()) == PRESSED) {
            plane.planeMove(1, -1);
        }
        if (KeyMointer.getKeyMap("a" + plane.getId()) == PRESSED) {
            plane.setRotate(plane.getRotate() + 5);
        }
        if (KeyMointer.getKeyMap("s" + plane.getId()) == PRESSED) {
            plane.planeMove(-1, 1);
        }
        if (KeyMointer.getKeyMap("d" + plane.getId()) == PRESSED) {
            plane.setRotate(plane.getRotate() - 5);
        }
        if (KeyMointer.getKeyMap("j" + plane.getId()) == PRESSED) {
            plane.shoot();
        }
        if (KeyMointer.getKeyMap("k" + plane.getId()) == PRESSED) {
        }
    }

    private void drawMsg(Graphics g) {
        g.drawString("生命: " + 1, 330, 50);
        g.drawString("积分: " + Config.INIT_SCORES, 330, 70);
        g.drawString(" ", 330, 90);
        //显示日志
        String msg = LogServer.get();
        if (Objects.nonNull(msg)) {
            g.drawString(msg, 230, 90);
        }
    }

    /**
     * 回收越界子弹
     */
    private void recyclingBullets() {
        bulletList.removeIf(bullet -> bullet.getPositionX() < -10
                || bullet.getPositionX() > WINDOW_SPLITER
                || bullet.getPositionY() < -10
                || bullet.getPositionY() >WINDOW_SPLITER);
    }


    //游戏画面，开启双缓冲，嵌入到左面板
    private class GamePanel extends JPanel {
        public GamePanel() {
            super(true);
            lanch();
        }

        public void lanch() {
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    repaint();
                }
            }).start();
        }

        //画图函数，这个是重写paint，Frame类的内置函数
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            drawPlane(g);
            drawBullets(g);
            drawMsg(g);
            drawCircleBullte(g);
        }
    }

    private JComponent createSplitPanel() {
        // 创建分隔面板
        JSplitPane splitPane = new JSplitPane();

        // 设置左右两边显示的组件
        final JTabbedPane tabbedPane2 = new JTabbedPane();
        tabbedPane2.addTab("全部", createTextPanel());
        tabbedPane2.addTab("队伍", createTextPanel());
        tabbedPane2.addTab("悄悄话", createTextPanel());

        splitPane.setLeftComponent(new GamePanel());
        splitPane.setRightComponent(tabbedPane2);

        splitPane.setContinuousLayout(true);
        splitPane.setDividerLocation(WINDOW_SPLITER);
        splitPane.setEnabled(false);

        return splitPane;
    }

    /**
     * 创建一个面板，面板中心显示一个标签，用于表示某个选项卡需要显示的内容
     */
    private JComponent createTextPanel() {
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
