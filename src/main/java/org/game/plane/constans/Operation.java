package org.game.plane.constans;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Operation {
    SHOOT('j'),
    ATTACK('k');

    public final char operation;

    public static Operation getByChar(char operation) {
        return Arrays.stream(values()).filter(e -> e.operation == operation).findFirst().orElse(null);
    }
}
