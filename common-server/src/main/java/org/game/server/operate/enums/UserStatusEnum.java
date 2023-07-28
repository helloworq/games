package org.game.server.operate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    OFFLINE(0, "离线"),
    ONLINE(1, "在线"),
    ;

    private final int code;
    private final String des;
}
