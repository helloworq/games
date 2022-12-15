package org.game.plane.run;

import org.game.plane.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DoubleBuff extends Frame {
    private static final int WIDTH = 800;//窗口宽
    private static final int HEIGHT = 600;//窗口高
    private int x = 50;//圆的横坐标
    private int y = 50;//圆的纵坐标
    private static int rotate = 0;//角度

    //缓存图片
    private static BufferedImage plane = null;

    static {
        try {
            plane = ImageIO.read(new File("D:\\DerbyCode\\games\\client\\src\\main\\resources\\images\\plane\\复古飞机.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchFrame() {
        setSize(WIDTH, HEIGHT);
        setTitle("DoubleBufferTest");

        setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setResizable(false);//设置不可改变窗口大小
        this.setBackground(Color.white);
        setVisible(true);
        while (true) {
            repaint();//重画
            try {
                Thread.sleep(10);//每隔50ms重画一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 重写update方法，实现双缓冲，以消除闪烁
     */
    private static Image offScreenImage = null;//用于实现双缓冲

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(WIDTH, HEIGHT);//创建一张大小和窗口大小一样的虚拟图片
        }
        Graphics gOffScreen = offScreenImage.getGraphics();//获得画笔
        //刷新背景，否则物体运动痕迹会保留
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.white);

        //清屏
        gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);
        drawRect(gOffScreen);

        gOffScreen.setColor(c);
        paint(gOffScreen);//画到虚拟图片上
        g.drawImage(offScreenImage, 0, 0, null);//把图片画到屏幕上
    }


    private void drawRect(Graphics g) {
        BufferedImage image = ImageUtil.rotateImage(plane, rotate);
        g.drawImage(image, x, y, null);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();//取出原前景色
        g.setColor(Color.RED);//设置前景色
        g.setColor(c);//恢复原前景色
        y += 1;
    }


    public static void main(String[] args) {
        DoubleBuff tc = new DoubleBuff();
        tc.launchFrame();
    }
}
