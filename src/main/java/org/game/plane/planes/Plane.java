package org.game.plane.planes;

import lombok.Data;
import org.game.plane.PlaneClient;
import org.game.plane.bullets.Bullet;
import org.game.plane.common.FlyingObj;
import org.game.plane.constans.Config;
import org.game.plane.constans.Direction;
import org.game.plane.weapon.Weapon;


@Data
public class Plane extends FlyingObj {
    private Weapon weapon = new Weapon();

    public Plane() {
    }

    public Plane(int startX, int startY, Direction direction) {
        super(startX, startY, direction, Config.INIT_ALIVE);
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
