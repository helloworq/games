package org.game.plane.common.bullets;

import lombok.Data;
import org.game.plane.common.FlyingObj;
import org.game.plane.constans.Config;
import org.game.plane.constans.Direction;

@Data
public class Bullet extends FlyingObj {
    private int speed;

    public Bullet(int startX, int startY, Direction direction, Integer imageId) {
        super(startX, startY, direction, Config.INIT_ALIVE, null, imageId);
    }

    public void move() {
        Direction direction = getDirection();
        switch (direction) {
            case UP:
                setPositionY(getPositionY() - 5);
                break;
            case DOWN:
                setPositionY(getPositionY() + 5);
                break;
            case LEFT:
                setPositionX(getPositionX() - 5);
                break;
            case RIGHT:
                setPositionX(getPositionX() + 5);
                break;
        }
    }
}
