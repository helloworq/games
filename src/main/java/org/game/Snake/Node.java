package org.game.Snake;

import lombok.Data;

@Data
public class Node {
    int position_X;
    int position_Y;

    public Node() {
    }

    public Node(int startX, int startY) {
        this.position_X = startX;
        this.position_Y = startY;
    }
}
