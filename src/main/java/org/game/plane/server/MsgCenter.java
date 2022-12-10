package org.game.plane.server;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * 关联用户以及相关操作
 */
public class MsgCenter {
    private static final Map<Channel, String> USER_POOL = new ConcurrentHashMap<>();
    //用户
    public static final String ALL = "公告 ";
    //操作符前缀
    public static final String OP_SPLIT = "-";
    public static final String SPEAK = ": ";
    private static final Map<String, BiConsumer<Channel, String>> OperationPrefix =
            Map.of("@@ChangeName", (channel, name) -> USER_POOL.computeIfPresent(channel, (k, v) -> name));

    public static void execIfDec(Channel channel, String msg) {
        if (StringUtil.isNullOrEmpty(msg)) {
            return;
        }
        String[] data = msg.split(OP_SPLIT);
        String op = data[0];
        if (data.length <= 1) {
            noticeAll(USER_POOL.get(channel), msg);//非操作输入视为广播消息
            return;
        }
        if (OperationPrefix.containsKey(op)) {
            OperationPrefix.get(op).accept(channel, data[1]);
        }
    }

    public static void addUser(Channel channel, String name) {
        USER_POOL.put(channel, name);
    }

    public static void removeUser(Channel channel) {
        USER_POOL.remove(channel);
    }

    public static void noticeAll(String name, String msg) {
        USER_POOL.forEach((k, v) -> k.writeAndFlush(new TextWebSocketFrame(name + SPEAK + msg)));
    }
}
