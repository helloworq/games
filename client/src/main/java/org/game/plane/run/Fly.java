package org.game.plane.run;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Fly {
    private static final int PRESSED = 1;
    private static final int RELEASED = 0;
    private static int x = 200;
    private static int y = 200;
    private static int rotate = -60;
    private static double planeSpeed = 5.0;
    //将所有按键事件通过标志位处理
    private static final Map<Character, Integer> keyMap = new ConcurrentHashMap<>();

    static {
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

    static class MyPanel extends JFrame {
        public MyPanel() {
            this.setBounds(0, 0, 400, 400); //设置窗体大小和位置
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
        }

        public void lanch() throws InterruptedException {
            while (true) {
                Thread.sleep(20);
                handleKeyMap();
                repaint();
            }
        }

        private void handleKeyMap() {
            if (keyMap.get('w') == PRESSED) {
                planeMove(-1, 1);
            }
            if (keyMap.get('a') == PRESSED) {
                rotate += 5;
            }
            if (keyMap.get('s') == PRESSED) {
                planeMove(1, -1);
            }
            if (keyMap.get('d') == PRESSED) {
                rotate -= 5;
            }
        }

        //根据飞机倾角计算运动位置
        private void planeMove(int xr, int yr) {
            int y1 = (int) (Math.sin(Math.PI * 2.0 * (rotate + 330.0) / 360.0) * planeSpeed);
            int x1 = (int) (Math.cos(Math.PI * 2.0 * (rotate + 330.0) / 360.0) * planeSpeed);
            x1 = xr * x1;//前进时置为相反数
            y1 = yr * y1;//后退时置为相反数


//            System.out.println("y1 = " + y1
//                    + " x1 = " + x1
//                    + " 倾角 = " + (rotate + 330)
//                    + " sin = " + Math.sin(Math.PI * 2.0 * (rotate + 330.0) / 360.0)
//                    + " cos = " + Math.cos(Math.PI * 2.0 * (rotate + 330.0) / 360.0)
//            );
            y += y1;
            x += x1;
        }

        @Override
        public void paint(Graphics g) {
            //逐个读取链表中的节点数据，循环打印节点
            super.paint(g);
            drawRect(g);
        }

        double angle = 0;

        private void drawRect(Graphics g) {
            g.fillArc(x, y, 50, 50, rotate, -60);
            angle = angle < 2 ? (angle + 1.0 / 256.0) : 0.0;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyPanel panel = new MyPanel();
        panel.lanch();
    }
}
