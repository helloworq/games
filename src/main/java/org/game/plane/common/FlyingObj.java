package org.game.plane.common;

import lombok.Data;

@Data
public class FlyingObj {
    private int positionX;
    private int positionY;

    public FlyingObj() {
    }

    public FlyingObj(int startX, int startY) {
        this.positionX = startX;
        this.positionY = startY;
    }
}
