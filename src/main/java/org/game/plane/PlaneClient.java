package org.game.plane;

import org.game.plane.bullets.Bullet;
import org.game.plane.event.KeyMointer;
import org.game.plane.planes.Plane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class PlaneClient extends JFrame {
    //蛇的初始坐标
    public static int StartPositionX = 240;
    public static int StartPositionY = 340;
    //机身大小
    public static int PlaneSize = 20;
    public static int BulletSize = 10;
    //使用链表维护蛇身节点数据
    public static LinkedList<Plane> planeList = new LinkedList<>();
    public static LinkedList<Bullet> bulletList = new LinkedList<>();
    //游戏速度
    public final static int GAME_SPEED = 20;
    //主飞机
    public static final Plane plane = new Plane(StartPositionX, StartPositionY);

    public PlaneClient() {
    }

    public static void main(String[] args) throws InterruptedException {
        PlaneClient planeClient = new PlaneClient();
        planeClient.lanch();
    }


    public void lanch() throws InterruptedException {
        this.setBounds(200, 100, 400, 400); //设置窗体大小和位置
        this.setVisible(true); //使用该属性才能显示窗体
        //实现程序运行关闭的功能
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });

        //初始化第一架飞机
        planeList.add(new Plane(StartPositionX, StartPositionY));
        //调用键盘
        addKeyListener(new KeyMointer());
        //开始
        while (true) {
            repaint();
            Thread.sleep(GAME_SPEED);
        }
    }

    private void drawPlane(Graphics g) {
//        for (Plane plane : planeList) {
//            System.out.println(plane.getPositionX());
            g.setColor(Color.gray);
            g.fillRect(plane.getPositionX(), plane.getPositionY(), PlaneSize, PlaneSize);
//        }
    }

    //画图函数，这个是重写paint，Frame类的内置函数
    @Override
    public void paint(Graphics g) {
        //逐个读取链表中的节点数据，循环打印节点
        //清屏
        g.clearRect(0, 0, 400, 400);
        drawPlane(g);
    }
}
