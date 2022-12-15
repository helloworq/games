package org.game.plane.common.weapon;

import lombok.Data;
import org.game.plane.common.bullets.Bullet;
import org.game.plane.constans.Direction;

@Data
public class NormlWeapon extends Weapon {

    public Bullet getBullet(int startX, int startY, Direction direction) {
        return new Bullet(startX, startY, direction, 5);
    }

    public Bullet getBullet(int startX, int startY, int rotate) {
        return new Bullet(startX, startY, rotate, 5);
    }
}
