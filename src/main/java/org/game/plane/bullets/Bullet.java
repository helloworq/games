package org.game.plane.bullets;

import lombok.Data;
import org.game.plane.PlaneClient;
import org.game.plane.common.FlyingObj;
import org.game.plane.constans.Config;
import org.game.plane.constans.Direction;

@Data
public class Bullet extends FlyingObj {
    private int speed;

    public Bullet(int startX, int startY, Direction direction) {
        super(startX, startY, direction, Config.INIT_ALIVE, null);
    }

    public void move() {
        Direction direction = getDirection();
        switch (direction) {
            case UP:
                setPositionY(getPositionY() - 10);
                break;
            case DOWN:
                setPositionY(getPositionY() + 10);
                break;
            case LEFT:
                setPositionX(getPositionX() - 10);
                break;
            case RIGHT:
                setPositionX(getPositionX() + 10);
                break;
        }
    }
}
