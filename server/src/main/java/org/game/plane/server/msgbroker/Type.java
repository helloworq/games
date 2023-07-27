package org.game.plane.server.msgbroker;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Type {

    @Getter
    @AllArgsConstructor
    public enum MsgType {
        CHAT(0, "聊天"),
        LOGIN(1, "登录"),
        LOGOUT(2, "登出"),
        OPERATION(3, "操作");

        private final int code;
        private final String type;
    }
}
