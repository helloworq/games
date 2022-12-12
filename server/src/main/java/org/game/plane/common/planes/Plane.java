package org.game.plane.common.planes;

import lombok.Data;
import org.game.plane.common.FlyingObj;
import org.game.plane.common.weapon.Weapon;
import org.game.plane.constans.Config;
import org.game.plane.constans.Direction;


@Data
public class Plane extends FlyingObj {
    private Weapon weapon = new Weapon();

    public Plane() {
    }

    public Plane(int startX, int startY, Direction direction, String id) {
        super(startX, startY, direction, Config.INIT_ALIVE, id);
    }
}
