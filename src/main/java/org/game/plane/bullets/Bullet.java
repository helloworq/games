package org.game.plane.bullets;

import lombok.Data;
import org.game.plane.common.FlyingObj;

@Data
public class Bullet extends FlyingObj {
    private int speed;
}
