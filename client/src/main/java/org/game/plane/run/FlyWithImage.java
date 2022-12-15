package org.game.plane.run;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.game.plane.ImageUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FlyWithImage {
    private static final int PRESSED = 1;
    private static final int RELEASED = 0;
    private static int x = 200;
    private static int y = 200;
    private static int rotate = 0;//角度
    private static double planeSpeed = 5.0;
    //将所有按键事件通过标志位处理
    private static final Map<Character, Integer> keyMap = new ConcurrentHashMap<>();
    //缓存图片
    private static BufferedImage plane = null;

    static {
        try {
            plane = ImageIO.read(new File("D:\\DerbyCode\\games\\client\\src\\main\\resources\\images\\plane\\复古飞机.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        keyMap.put('w', RELEASED);
        keyMap.put('a', RELEASED);
        keyMap.put('s', RELEASED);
        keyMap.put('d', RELEASED);
    }

    @Getter
    @AllArgsConstructor
    public static class Position {
        int x;
        int y;
    }

    static class MyPanel extends Frame {
        private static final int WIDTH = 800;//窗口宽
        private static final int HEIGHT = 600;//窗口高

        public MyPanel() {

        }

        public void lanch() throws InterruptedException {
            this.setBounds(0, 0, WIDTH, HEIGHT); //设置窗体大小和位置
            this.setVisible(true); //使用该属性才能显示窗体
            //实现程序运行关闭的功能
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyChar()) {
                        case 'w':
                            keyMap.put('w', PRESSED);
                            break;
                        case 'a':
                            keyMap.put('a', PRESSED);
                            break;
                        case 's':
                            keyMap.put('s', PRESSED);
                            break;
                        case 'd':
                            keyMap.put('d', PRESSED);
                            break;
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    switch (e.getKeyChar()) {
                        case 'w':
                            keyMap.put('w', RELEASED);
                            break;
                        case 'a':
                            keyMap.put('a', RELEASED);
                            break;
                        case 's':
                            keyMap.put('s', RELEASED);
                            break;
                        case 'd':
                            keyMap.put('d', RELEASED);
                            break;
                    }
                }
            });
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(-1);
                }
            });
            new Thread(() -> {
                while (true) {
                    repaint();//重画
                    handleKeyMap();
                    try {
                        Thread.sleep(10);//每隔50ms重画一次
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        private void handleKeyMap() {
            if (keyMap.get('w') == PRESSED) {
                planeMove(1, -1);
            }
            if (keyMap.get('a') == PRESSED) {
                rotate += 5;
            }
            if (keyMap.get('s') == PRESSED) {
                planeMove(-1, 1);
            }
            if (keyMap.get('d') == PRESSED) {
                rotate -= 5;
            }
        }

        //根据飞机倾角计算运动位置
        private void planeMove(int xr, int yr) {
            int y1 = (int) (Math.sin(Math.toRadians(rotate)) * planeSpeed);
            int x1 = (int) (Math.cos(Math.toRadians(rotate)) * planeSpeed);
            x1 = xr * x1;//前进时置为相反数
            y1 = yr * y1;//后退时置为相反数

            y += y1;
            x += x1;
        }

        private Image offScreenImage = null;//用于实现双缓冲

        @Override
        public void update(Graphics g) {
            if (offScreenImage == null) {
                offScreenImage = this.createImage(WIDTH, HEIGHT);//创建一张大小和窗口大小一样的虚拟图片
            }
            Graphics gOffScreen = offScreenImage.getGraphics();//获得画笔
            //刷新背景，否则物体运动痕迹会保留
            Color c = gOffScreen.getColor();
            gOffScreen.setColor(Color.white);

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
    }

    public static void main(String[] args) throws InterruptedException {
        //!!必须继承Frame！！！！
        MyPanel panel = new MyPanel();
        panel.lanch();
    }
}
