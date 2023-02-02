package org.game.plane.run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DoubleBuffPanel extends JFrame {

    public static void main(String[] args) throws InterruptedException {
        DoubleBuffPanel doubleBuffPanel = new DoubleBuffPanel();
    }

    public DoubleBuffPanel() throws InterruptedException {
        this.setBounds(0, 0, 400, 400); //设置窗体大小和位置
        this.setVisible(true); //使用该属性才能显示窗体
        //实现程序运行关闭的功能
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });
        MyPanel panel = new MyPanel();
        this.add(panel);
    }

    private class MyPanel extends JPanel {
        public MyPanel() throws InterruptedException {
            super(true);
            lanch();
        }

        @Override
        public void paint(Graphics g) {
            //逐个读取链表中的节点数据，循环打印节点
            super.paint(g);
            drawRect(g);
        }

        double angle = 0;

        public void lanch() throws InterruptedException {
            new Thread(
                    () -> {
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

        private void drawRect(Graphics g) {
            Run.Position position = getCirclePosition(0);
            Run.Position position2 = getCirclePosition(1.0 / 3.0);
            Run.Position position3 = getCirclePosition(2.0 / 3.0);
            Run.Position position4 = getCirclePosition(3.0 / 3.0);
            Run.Position position5 = getCirclePosition(4.0 / 3.0);
            Run.Position position6 = getCirclePosition(5.0 / 3.0);
            Run.Position position7 = getCirclePosition(6.0 / 3.0);

            g.drawRect(200, 200, 20, 20);

            g.drawRect(200 + position.x, 200 + position.y, 20, 20);
            g.drawRect(200 + position2.x, 200 + position2.y, 20, 20);
            g.drawRect(200 + position3.x, 200 + position3.y, 20, 20);
            g.drawRect(200 + position4.x, 200 + position4.y, 20, 20);
            g.drawRect(200 + position5.x, 200 + position5.y, 20, 20);
            g.drawRect(200 + position6.x, 200 + position6.y, 20, 20);
            g.drawRect(200 + position7.x, 200 + position7.y, 20, 20);

            angle = angle < 2 ? (angle + 1.0 / 512) : 0.0;
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

}
