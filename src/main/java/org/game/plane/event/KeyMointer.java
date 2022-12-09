package org.game.plane.event;

import org.game.plane.PlaneClient;
import org.game.plane.constans.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//键盘控制
public class KeyMointer extends KeyAdapter {

    public void keyPressed(KeyEvent e) {
        keyPress(e);
    }

    public void keyPress(KeyEvent e) {
        //根据键入的数据改变蛇方向控制符
        Direction direction = Direction.getByChar(e.getKeyChar());
        if (null == direction) {
            return;
        }
        switch (direction) {
            case UP:
                PlaneClient.plane.moveUp();
                break;
            case LEFT:
                PlaneClient.plane.moveLeft();
                break;
            case DOWN:
                PlaneClient.plane.moveDown();
                break;
            case RIGHT:
                PlaneClient.plane.moveRight();
                break;
        }
    }
}
