package org.game.plane.constans;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum Direction {
    UP('w', "w"),
    DOWN('s', "s"),
    LEFT('a', "a"),
    RIGHT('d', "d");

    public final char direction;
    public final String dir;

    public static Direction getByChar(char dir) {
        return Arrays.stream(Direction.values()).filter(e -> e.direction == dir).findFirst().orElse(null);
    }

    public static Direction getByString(String dir) {
        return Arrays.stream(Direction.values()).filter(e -> Objects.equals(e.dir, dir)).findFirst().orElse(null);
    }
}
