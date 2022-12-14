package org.game.plane.run;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Shape {

    private static int x = 200;
    private static int y = 200;
    private static int rotate = -60;
    private static double planeSpeed = 5.0;

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
                            planeMove(-1, 1);
                            break;
                        case 'a':
                            rotate += 20;
                            break;
                        case 's':
                            planeMove(1, -1);
                            break;
                        case 'd':
                            rotate -= 20;
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
                repaint();
            }
        }

        //根据飞机倾角计算运动位置
        private void planeMove(int xr, int yr) {
            int y1 = (int) (Math.sin(Math.PI * 2.0 * (rotate + 330.0) / 360.0) * planeSpeed);
            int x1 = (int) (Math.cos(Math.PI * 2.0 * (rotate + 330.0) / 360.0) * planeSpeed);
            x1 = xr * x1;
            y1 = yr * y1;


            System.out.println("y1 = " + y1
                    + " x1 = " + x1
                    + " 倾角 = " + (rotate + 330)
                    + " sin = " + Math.sin(Math.PI * 2.0 * (rotate + 330.0) / 360.0)
                    + " cos = " + Math.cos(Math.PI * 2.0 * (rotate + 330.0) / 360.0)
            );
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
            Run.Position position = getCirclePosition(0.0);
            Run.Position position2 = getCirclePosition(1.0 / 3.0);
            Run.Position position3 = getCirclePosition(2.0 / 3.0);
            Run.Position position4 = getCirclePosition(1.0);
            Run.Position position5 = getCirclePosition(4.0 / 3.0);
            Run.Position position6 = getCirclePosition(5.0 / 3.0);

            //g.drawRect(200, 200, 20, 20);
            //g.drawOval(200, 200, 20, 20);
            //g.drawArc(200, 200, 20, 20, 225, 315);
            g.fillArc(x, y, 50, 50, rotate, -60);

            g.drawOval(x + position.x, y + position.y, 20, 20);
            g.drawRect(x + position2.x, y + position2.y, 20, 20);
            g.fillArc(x + position3.x, y + position3.y, 20, 20, rotate - 30, -60);
            g.drawRect(x + position4.x, y + position4.y, 20, 20);
            g.drawOval(x + position5.x, y + position5.y, 20, 20);
            g.drawRect(x + position6.x, y + position6.y, 20, 20);

            angle = angle < 2 ? (angle + 1.0 / 256.0) : 0.0;
        }

        public Run.Position getCirclePosition(double offset) {
            //根据直径计算坐标(加上距圆心偏移量即为坐标)
            double curAngle = offset + angle;

            double length = 100.0;
            int y = (int) (Math.sin(curAngle * Math.PI) * length);
            int x = (int) (Math.cos(curAngle * Math.PI) * length);

            //System.out.println("角度: " + angle + " X坐标: " + x + " Y坐标: " + y + " 计算和" + Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
            return new Run.Position(x, y);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyPanel panel = new MyPanel();
        panel.lanch();
    }
}
