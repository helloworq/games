package org.game.plane.run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DoubleBuff extends JPanel {
    Image doubleBuffer;
    int clickCount;
    int i = 1;
    private static final Dimension pSize = new Dimension(400, 400);

    public void paint(Graphics g) {
        if (doubleBuffer == null) {
            doubleBuffer = createImage(this.getWidth(), this.getHeight());
        }
        g.drawImage(doubleBuffer, 0, 0, null);
        drawRect(g);
    }

    public Dimension getPreferredSize() {
        return pSize;
    }

    public void changeInternalStatus() {
        Graphics g2 = doubleBuffer.getGraphics();

        g2.drawLine(0, clickCount, doubleBuffer.getWidth(null), clickCount);
        clickCount += 5;
        g2.dispose();
        repaint();
    }

    double angle = 0;

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

        angle = angle < 2 ? (angle + 1.0 / 128.0) : 0.0;
    }

    public Run.Position getCirclePosition(double offset) {
        //根据直径计算坐标(加上距圆心偏移量即为坐标)
        double curAngle = offset + angle;

        double length = 100.0;
        int y = (int) (Math.sin(curAngle * Math.PI) * length);
        int x = (int) (Math.cos(curAngle * Math.PI) * length);

        if (i==1){
            System.out.println(i);
            i++;
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    repaint();
                }
            }).start();
        }
        //System.out.println("角度: " + angle + " X坐标: " + x + " Y坐标: " + y + " 计算和" + Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        return new Run.Position(x, y);
    }



    public static void main(String[] args) {
        final DoubleBuff p = new DoubleBuff();
        JFrame f = new JFrame();
        JMenuBar mb = new JMenuBar();
        JMenu m = new JMenu("Test");
        mb.add(m);
        JMenuItem mi = new JMenuItem("Draw in off screen");
        m.add(mi);
        mi.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                p.changeInternalStatus();
            }
        });
        f.setJMenuBar(mb);
        f.setContentPane(p);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.show();
    }
}
