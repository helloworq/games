package org.game.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Direction {
    UP('w'),
    DOWN('s'),
    LEFT('a'),
    RIGHT('d');

    public final char direction;

    public static Direction getByChar(char dir) {
        return Arrays.stream(Direction.values()).filter(e -> e.direction == dir).findFirst().orElse(null);
    }
}
