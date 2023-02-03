package org.game.plane.common.planes;

import lombok.Data;
import org.game.plane.common.weapon.NormlWeapon;
import org.game.plane.frame.PlaneClient;
import org.game.plane.common.bullets.Bullet;
import org.game.plane.common.FlyingObj;
import org.game.plane.constans.Config;
import org.game.plane.constans.Direction;


@Data
public class Plane extends FlyingObj {
    private NormlWeapon weapon = new NormlWeapon();

    public Plane() {
    }

    public Plane(int startX, int startY, String id) {
        super(startX, startY, Config.INIT_ALIVE, id, 6);
    }

    public void shoot() {
        Bullet bullet = weapon.getBullet(getPositionX()+45, getPositionY()+30);
        bullet.setRotate(getRotate());
        PlaneClient.bulletList.add(bullet);
    }

    public void attack() {
        Bullet bullet = weapon.getBullet(getPositionX(), getPositionY(), getRotate());
        PlaneClient.bulletList.add(bullet);
    }
}
