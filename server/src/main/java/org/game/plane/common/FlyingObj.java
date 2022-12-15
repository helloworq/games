package org.game.plane.common;

import lombok.Data;
import org.game.plane.constans.Direction;

@Data
public class FlyingObj {
    private String id;
    private int positionX;
    private int positionY;
    private int live;
    private Integer imageId;
    private int rotate = 0;//角度
    private static double planeSpeed = 5.0;
}
