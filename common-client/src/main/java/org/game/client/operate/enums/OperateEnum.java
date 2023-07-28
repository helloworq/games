package org.game.client.operate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperateEnum {
    LOGIN(0, "登录"),
    LOGOUT(1, "登出"),
    CHAT(2, "聊天"),
    OPERATION(3, "更名"),
    USER_LIST(4, "用户列表"),
    ;

    private final int code;
    private final String type;
}
