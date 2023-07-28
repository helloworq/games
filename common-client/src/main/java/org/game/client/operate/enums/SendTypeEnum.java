package org.game.client.operate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SendTypeEnum {
    ONE_2_ONE(0, "一对一"),
    ONE_2_GROUP(1, "一对多"),
    TO_ALL(2, "通知全体用户"),
    ;

    private final Integer code;
    private final String des;
}
