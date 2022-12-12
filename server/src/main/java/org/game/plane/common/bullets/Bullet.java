package org.game.plane.common.bullets;

import lombok.Data;
import org.game.plane.common.FlyingObj;
import org.game.plane.constans.Config;
import org.game.plane.constans.Direction;

@Data
public class Bullet extends FlyingObj {
    private int speed;

    public Bullet(int startX, int startY, Direction direction) {
        super(startX, startY, direction, Config.INIT_ALIVE, null);
    }
}
