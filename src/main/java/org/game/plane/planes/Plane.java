package org.game.plane.planes;

import org.game.plane.common.FlyingObj;

public class Plane extends FlyingObj {
    public Plane() {

    }

    public Plane(int startX, int startY) {
        super(startX, startY);
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
