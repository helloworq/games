package org.game.plane.run;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.game.plane.event.KeyMointer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 测试各种运动曲线函数的生成
 */
public class Run {

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
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(-1);
                }
            });
        }

        public void lanch() throws InterruptedException {
            while (true) {
                Thread.sleep(50);
                repaint();
            }
        }

        @Override
        public void paint(Graphics g) {
            //逐个读取链表中的节点数据，循环打印节点
            super.paint(g);
            g.drawString("我", 200, 200);
            g.drawString("环绕", 200 + getCirclePosition().x, 200 + getCirclePosition().y);
        }

        double angle = 0;

        public Position getCirclePosition() {
            //根据直径计算坐标(加上距圆心偏移量即为坐标)
            double length = 100.0;
            int y = (int) (Math.sin(angle * Math.PI) * length);
            int x = (int) (Math.cos(angle * Math.PI) * length);
            Position position = new Position(x, y);

            System.out.println("角度: " + angle + " X坐标: " + x + " Y坐标: " + y + " 计算和" + Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
            if (angle < 2) {
                angle = angle + 1.0 / 64.0;
            } else {
                angle = 0.0;
            }
            return position;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyPanel panel = new MyPanel();
        panel.lanch();
    }
}