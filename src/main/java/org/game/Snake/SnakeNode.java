package org.game.Snake;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.game.SnakeClient;

import java.awt.*;

@Data
public class SnakeNode extends Node {
    public SnakeNode() {

    }

    public SnakeNode(int startX, int startY) {
        this.position_X = startX;
        this.position_Y = startY;
    }

    private int position_X;
    private int position_Y;
    public static Color snakeColor = SnakeClient.SnakeColor;
}
