package org.game.plane.constans;

import java.util.Map;

public class Config {
    public static final int INIT_ALIVE = 1;
    public static final int INIT_SCORES = 0;

    public static final Map<Character, String> KEY_MAP = Map.of(
            'w', "w",
            's', "s",
            'a', "a",
            'd', "d",
            'j', "j",
            'k', "k"
    );
}
