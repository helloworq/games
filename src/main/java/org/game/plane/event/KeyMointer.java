package org.game.plane.event;

import org.game.plane.PlaneClient;
import org.game.plane.constans.Direction;
import org.game.plane.constans.Operation;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

//键盘控制
public class KeyMointer extends KeyAdapter {

    public void keyPressed(KeyEvent e) {
        keyPress(e);
    }

    public void keyPress(KeyEvent e) {
        //根据键入的数据改变蛇方向控制符
        Direction direction = Direction.getByChar(e.getKeyChar());
        if (Objects.nonNull(direction)) {
            handleMove(direction);
            return;
        }
        Operation operation = Operation.getByChar(e.getKeyChar());
        if (Objects.nonNull(operation)) {
            handleShoot(operation);
            return;
        }
    }

    private void handleMove(Direction direction) {
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

    private void handleShoot(Operation operation) {
        switch (operation) {
            case SHOOT:
                PlaneClient.plane.shoot();
                break;
            case ATTACK:
                PlaneClient.plane.attack();
                break;
        }
    }
}
