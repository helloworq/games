package org.game.plane.frame;

import org.game.plane.ImageUtil;
import org.game.plane.common.bullets.Bullet;
import org.game.plane.constans.Config;
import org.game.plane.constans.Direction;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlaneClient extends Frame {
    //窗体参数
    public static final int X = 400;
    public static final int Y = 400;
    public static final int deviation = 10;
    //蛇的初始坐标
    public int StartPositionX = 200;
    public int StartPositionY = 200;
    //机身大小
    public static int PlaneSize = 20;
    public static int BulletSize = 10;
    //使用链表维护蛇身节点数据
    private static final LinkedList<Plane> planeList = new LinkedList<>();
    public static List<Bullet> bulletList = new CopyOnWriteArrayList<>();
    //游戏速度
    public final static int GAME_SPEED = 20;
    private static final int PRESSED = 1;
    private static final int RELEASED = 0;
    //主飞机
    //public static final Plane plane = new Plane(StartPositionX, StartPositionY, Direction.UP, "");
    //画布
    private Image offScreenImage = null;//用于实现双缓冲

    public PlaneClient() {
        this.setBounds(0, 0, X, Y); //设置窗体大小和位置
        this.setVisible(true); //使用该属性才能显示窗体
        //实现程序运行关闭的功能
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });
        //调用键盘
        addKeyListener(KeyMointer.getInstance(this));
    }

    public void lanch() throws InterruptedException {
        //开始
        while (true) {
            repaint();
            Thread.sleep(GAME_SPEED);
        }
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
//            g.setColor(Color.gray);
//            g.fillRect(plane.getPositionX(), plane.getPositionY(), PlaneSize, PlaneSize);
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

    private void drawCircleBullte(Graphics g) {
        if (Objects.nonNull(planeList) && !planeList.isEmpty()) {
            Plane plane = planeList.get(0);
            Run.Position position = getCirclePosition();
            g.drawString("环绕", plane.getPositionX() + position.getX(), plane.getPositionY() + position.getY());
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
        if (KeyMointer.keyMap.get('w') == PRESSED) {
            plane.planeMove(1, -1);
        }
        if (KeyMointer.keyMap.get('a') == PRESSED) {
            plane.setRotate(plane.getRotate() + 5);
        }
        if (KeyMointer.keyMap.get('s') == PRESSED) {
            plane.planeMove(-1, 1);
        }
        if (KeyMointer.keyMap.get('d') == PRESSED) {
            plane.setRotate(plane.getRotate() - 5);
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
        bulletList.removeIf(bullet -> bullet.getPositionX() < deviation
                || bullet.getPositionX() > (X - deviation)
                || bullet.getPositionY() < deviation
                || bullet.getPositionY() > (Y - deviation));
    }

    //画图函数，这个是重写paint，Frame类的内置函数
    @Override
    public void update(Graphics g) {
        //逐个读取链表中的节点数据，循环打印节点
        if (offScreenImage == null) {
            offScreenImage = this.createImage(X, Y);//创建一张大小和窗口大小一样的虚拟图片
        }
        Graphics gOffScreen = offScreenImage.getGraphics();//获得画笔
        //刷新背景，否则物体运动痕迹会保留
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.white);
        gOffScreen.fillRect(0, 0, X, Y);

        drawPlane(gOffScreen);
        drawBullets(gOffScreen);
        drawMsg(gOffScreen);
        drawCircleBullte(gOffScreen);


        gOffScreen.setColor(c);
        paint(gOffScreen);//画到虚拟图片上
        g.drawImage(offScreenImage, 0, 0, null);//把图片画到屏幕上
    }
}
