package org.game.snake.event;

import org.game.snake.SnakeClient;
import org.game.snake.constants.Direction;

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
            case UP: {
                if (SnakeClient.SnakeDirection.equals(Direction.LEFT) || SnakeClient.SnakeDirection.equals(Direction.RIGHT))
                    SnakeClient.SnakeDirection = Direction.UP;
            }
            break;
            case LEFT: {
                if (SnakeClient.SnakeDirection.equals(Direction.UP) || SnakeClient.SnakeDirection.equals(Direction.DOWN))
                    SnakeClient.SnakeDirection = Direction.LEFT;
            }
            break;
            case DOWN: {
                if (SnakeClient.SnakeDirection.equals(Direction.LEFT) || SnakeClient.SnakeDirection.equals(Direction.RIGHT))
                    SnakeClient.SnakeDirection = Direction.DOWN;
            }
            break;
            case RIGHT: {
                if (SnakeClient.SnakeDirection.equals(Direction.UP) || SnakeClient.SnakeDirection.equals(Direction.DOWN))
                    SnakeClient.SnakeDirection = Direction.RIGHT;
            }
            break;
        }
    }
}
