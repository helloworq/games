package org.game.plane;

import org.game.plane.common.bullets.Bullet;
import org.game.plane.constans.Config;
import org.game.plane.constans.Direction;
import org.game.plane.event.KeyMointer;
import org.game.plane.log.LogServer;
import org.game.plane.common.planes.Plane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlaneClient extends JFrame {
    //窗体参数
    public static final int X = 400;
    public static final int Y = 400;
    public static final int deviation = 10;
    //蛇的初始坐标
    public static int StartPositionX = 240;
    public static int StartPositionY = 340;
    //机身大小
    public static int PlaneSize = 20;
    public static int BulletSize = 10;
    //使用链表维护蛇身节点数据
    private static final LinkedList<Plane> planeList = new LinkedList<>();
    public static List<Bullet> bulletList = new CopyOnWriteArrayList<>();
    //游戏速度
    public final static int GAME_SPEED = 20;
    //主飞机
    public static final Plane plane = new Plane(StartPositionX, StartPositionY, Direction.UP, "");

    public PlaneClient() {
    }

    public static void main(String[] args) throws InterruptedException {
        PlaneClient planeClient = new PlaneClient();
        planeClient.lanch();
    }


    public void lanch() throws InterruptedException {
        this.setBounds(200, 100, X, Y); //设置窗体大小和位置
        this.setVisible(true); //使用该属性才能显示窗体
        //实现程序运行关闭的功能
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });

        //初始化第一架飞机
//        planeList.add(plane);
        //调用键盘
        addKeyListener(new KeyMointer(this));
        //开始
        while (true) {
            repaint();
            Thread.sleep(GAME_SPEED);
        }
    }

    public static void addPlane(Plane plane) {
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
            g.setColor(Color.gray);
            g.fillRect(plane.getPositionX(), plane.getPositionY(), PlaneSize, PlaneSize);
        }
    }

    private void drawBullets(Graphics g) {
        for (Bullet bullet : bulletList) {
            recyclingBullets();
            bullet.move();
            g.setColor(Color.black);
            g.fillRect(bullet.getPositionX(), bullet.getPositionY(), PlaneSize, PlaneSize);
        }
    }

    private void drawMsg(Graphics g) {
        g.drawString("生命: " + plane.getLive(), 330, 50);
        g.drawString("积分: " + Config.INIT_SCORES, 330, 70);
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
        bulletList.removeIf(bullet -> bullet.getPositionX() < deviation
                || bullet.getPositionX() > (X - deviation)
                || bullet.getPositionY() < deviation
                || bullet.getPositionY() > (Y - deviation));
    }

    //画图函数，这个是重写paint，Frame类的内置函数
    @Override
    public void paint(Graphics g) {
        //逐个读取链表中的节点数据，循环打印节点
        g.clearRect(0, 0, 400, 400);//清屏
        drawPlane(g);
        drawBullets(g);
        drawMsg(g);
    }
}
