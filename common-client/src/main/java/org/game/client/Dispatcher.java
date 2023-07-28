package org.game.client;


import org.game.api.entity.Message;
import org.game.client.operate.chat.OperateChat;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 从客户端发来的请求在此进行转发
 */
public class Dispatcher {
    private static final Map<Integer, Consumer<Message>> OperateDispatcher = new HashMap<>();

    static {
        OperateDispatcher.put(5, OperateChat::showInWindow);
    }

    public static void doDispatcher(Message message) {
        int code = message.getOperateCode();
        OperateDispatcher.getOrDefault(code, (e) -> {
        }).accept(message);
    }
}