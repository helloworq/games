package org.game.plane.constans;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum Operation {
    SHOOT('j', "j"),
    ATTACK('k', "k");

    public final char operation;
    public final String opera;

    public static Operation getByChar(char operation) {
        return Arrays.stream(values()).filter(e -> e.operation == operation).findFirst().orElse(null);
    }

    public static Operation getByString(String operation) {
        return Arrays.stream(values()).filter(e -> Objects.equals(e.opera, operation)).findFirst().orElse(null);
    }
}
