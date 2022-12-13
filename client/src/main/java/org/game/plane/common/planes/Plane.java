package org.game.plane.common.planes;

import lombok.Data;
import org.game.plane.frame.PlaneClient;
import org.game.plane.common.bullets.Bullet;
import org.game.plane.common.FlyingObj;
import org.game.plane.constans.Config;
import org.game.plane.constans.Direction;
import org.game.plane.common.weapon.Weapon;


@Data
public class Plane extends FlyingObj {
    private Weapon weapon = new Weapon();

    public Plane() {
    }

    public Plane(int startX, int startY, Direction direction, String id) {
        super(startX, startY, direction, Config.INIT_ALIVE, id);
    }

    public void shoot() {
        Bullet bullet = weapon.getBullet(getPositionX(), getPositionY(), getDirection());
        PlaneClient.bulletList.add(bullet);
    }

    public void attack() {
        Bullet bullet = weapon.getBullet(getPositionX(), getPositionY(), getDirection());
        PlaneClient.bulletList.add(bullet);
    }
}
