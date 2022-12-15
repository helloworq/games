package org.game.plane.common;

import lombok.Data;
import org.game.plane.constans.Direction;

@Data
public class FlyingObj {
    private String id;
    private int positionX;
    private int positionY;
    private Direction direction;
    private int live;
    private Integer imageId;
    private int rotate = 0;//角度
    private static double planeSpeed = 5.0;

    public FlyingObj() {
    }

    public FlyingObj(int startX
            , int startY
            , Direction direction
            , int live
            , String id
            , Integer imageId) {
        this.positionX = startX;
        this.positionY = startY;
        this.direction = direction;
        this.live = live;
        this.id = id;
        this.imageId = imageId;
    }

    //根据飞机倾角计算运动位置
    public void planeMove(int xr, int yr) {
        int y1 = (int) (Math.sin(Math.toRadians(rotate)) * planeSpeed);
        int x1 = (int) (Math.cos(Math.toRadians(rotate)) * planeSpeed);
        x1 = xr * x1;//前进时置为相反数
        y1 = yr * y1;//后退时置为相反数

        setPositionX(getPositionX() + x1);
        setPositionY(getPositionY() + y1);
    }

    public void moveUp() {
        setPositionY(getPositionY() - 10);
    }

    public void moveDown() {
        setPositionY(getPositionY() + 10);
    }

    public void moveLeft() {
        setPositionX(getPositionX() - 10);
    }

    public void moveRight() {
        setPositionX(getPositionX() + 10);
    }
}
