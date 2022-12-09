package org.game.Snake;

import lombok.Data;
import org.game.SnakeClient;

import java.awt.*;

@Data
public class SnakeNode extends Node {
    public SnakeNode() {

    }

    public SnakeNode(int startX, int startY) {
        super(startX, startY);
    }

    public static Color snakeColor = SnakeClient.SnakeColor;
}
