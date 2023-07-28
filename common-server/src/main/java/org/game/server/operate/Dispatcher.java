package org.game.server.operate;

import org.game.server.entity.Message;
import org.game.server.operate.chat.OperateChat;
import org.game.server.operate.normal.OperateUserInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 从客户端发来的请求在此进行转发
 */
public class Dispatcher {
    private static final Map<Integer, Consumer<Message>> OperateDispatcher = new HashMap<>();

    static {
        OperateDispatcher.put(2, OperateChat::chat);
        OperateDispatcher.put(3, OperateUserInfo::changeUsername);
        OperateDispatcher.put(4, OperateUserInfo::getUserList);
    }

    public static void doDispatcher(Message message) {
        int code = message.getOperateCode();
        OperateDispatcher.getOrDefault(code, (e) -> {}).accept(message);
    }
}
