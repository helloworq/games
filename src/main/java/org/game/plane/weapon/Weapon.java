package org.game.plane.weapon;

import lombok.Data;
import org.game.plane.bullets.Bullet;
import org.game.plane.constans.Direction;

@Data
public class Weapon {
    private Bullet bullet;

    public Bullet getBullet(int startX, int startY, Direction direction) {
        return new Bullet(startX, startY, direction);
    }
}
