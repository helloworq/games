package org.game.plane.server.client;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * 接收并处理来自服务端消息
 */
public class ClientMsgCenter {
    //用户
    public static final String ALL = "公告: ";
    //操作符前缀
    public static final String OP_SPLIT = "-";
    public static final String SPEAK = ": ";
    public static final String MSG_CHANGE_NAME = "@@ChangeName";
    public static final String MSG_SYSTEM = "@@MsgFromSystem";
    public static final String MSG_USER = "@@MsgFromUser";
    public static final String MSG_OPERATE = "@@MsgOperate";
    private static final Map<String, BiConsumer<Channel, String>> OperationPrefix =
            Map.of(MSG_SYSTEM, (channel, msg) -> System.out.println(ALL + msg.split(OP_SPLIT)[1]),
                    MSG_USER, (channel, msg) -> System.out.println(msg.split(OP_SPLIT)[2]
                            + SPEAK + msg.split(OP_SPLIT)[1]),
                    MSG_OPERATE, (channel, msg) -> System.out.println(msg.split(OP_SPLIT)[2]
                            + SPEAK + msg.split(OP_SPLIT)[1]));

    public static void execIfDec(Channel channel, String msg) {
        System.out.println("客户端收取消息->" + msg);
        if (StringUtil.isNullOrEmpty(msg)) {
            return;
        }
        String[] data = msg.split(OP_SPLIT);
        String op = data[0];
        if (data.length <= 1) {
            //非预定义消息不处理
            return;
        }
        if (OperationPrefix.containsKey(op)) {
            OperationPrefix.get(op).accept(channel, msg);
        }
    }
}
