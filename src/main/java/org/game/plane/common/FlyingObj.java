package org.game.plane.common;

import lombok.Data;
import org.game.plane.constans.Direction;

@Data
public class FlyingObj {
    private int positionX;
    private int positionY;
    private Direction direction;
    private int live;

    public FlyingObj() {
    }

    public FlyingObj(int startX, int startY, Direction direction, int live) {
        this.positionX = startX;
        this.positionY = startY;
        this.direction = direction;
        this.live = live;
    }

    public void moveUp() {
        setPositionY(getPositionY() - 10);
    }

    public void moveDown() {
        setPositionY(getPositionY() + 10);
    }

    public void moveLeft() {
        setPositionX(getPositionX() - 10);
    }

    public void moveRight() {
        setPositionX(getPositionX() + 10);
    }
}
