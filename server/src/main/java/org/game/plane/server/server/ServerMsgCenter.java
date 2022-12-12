package org.game.plane.server.server;

import com.alibaba.fastjson2.JSON;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;
import org.game.plane.common.planes.Plane;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

/**
 * 接收并处理来自客户端消息
 */
public class ServerMsgCenter {
    private static final Map<Channel, String> USER_POOL = new ConcurrentHashMap<>();
    private static final List<Plane> PLANE_LIST = new CopyOnWriteArrayList<>();
    //用户
    public static final String ALL = "公告: ";
    //操作符前缀
    public static final String OP_SPLIT = "-";
    public static final String SPEAK = ": ";
    public static final String MSG_CHANGE_NAME = "@@ChangeName";
    public static final String MSG_SYSTEM = "@@MsgFromSystem";
    public static final String MSG_USER = "@@MsgFromUser";
    public static final String MSG_OPERATE = "@@MsgOperate";
    public static final String OPERATE = "@@Operate";
    public static final String NEW_PLANE = "@@NewPlane";
    public static final String PLANE_LIST_INFO = "@@PlaneListInfo";
    private static final Map<String, BiConsumer<Channel, String>> OperationPrefix =
            Map.of(MSG_CHANGE_NAME, (channel, name) ->
                            USER_POOL.computeIfPresent(channel, (k, v) -> name.split(OP_SPLIT)[1]),
                    MSG_SYSTEM, (channel, msg) -> notice4System(getUser(channel), msg),
                    MSG_USER, (channel, msg) -> notice4User(getUser(channel), msg),
                    MSG_OPERATE, (channel, msg) -> notice4User(getUser(channel), msg),
                    OPERATE, (channel, msg) -> notice4User(getUser(channel), msg.replace(OPERATE, MSG_OPERATE)),
                    NEW_PLANE, (channel, msg) -> createPlane(channel, msg));

    public static void execIfDec(Channel channel, String msg) {
        if (StringUtil.isNullOrEmpty(msg)) {
            return;
        }
        String[] data = msg.split(OP_SPLIT);
        String op = data[0];
        if (data.length <= 1) {
            return;
        }
        if (OperationPrefix.containsKey(op)) {
            OperationPrefix.get(op).accept(channel, msg);
        }
    }

    //维护新进入的客户端的飞机，并且广播通知其他客户端
    public static void createPlane(Channel channel, String msg) {
        try {
            String planeJson = msg.split(OP_SPLIT)[1];
            Plane plane = JSON.parseObject(planeJson, Plane.class);
            PLANE_LIST.add(plane);
            notice4System(null, PLANE_LIST_INFO + OP_SPLIT + JSON.toJSONString(PLANE_LIST));
        } catch (Exception e) {
            System.out.println("转换失败");
        }
    }

    private static String getUser(Channel channel) {
        return USER_POOL.get(channel);
    }

    public static void addUser(Channel channel, String name) {
        USER_POOL.put(channel, name);
    }

    public static void removeUser(Channel channel) {
        String id = USER_POOL.get(channel);
        PLANE_LIST.removeIf(e -> e.getId().equals(id));
        USER_POOL.remove(channel);
    }

    private static void notice4System(String name, String msg) {
        USER_POOL.forEach((k, v) -> k.writeAndFlush(new TextWebSocketFrame(msg)));
    }

    private static void notice4User(String name, String msg) {
        USER_POOL.forEach((k, v) -> k.writeAndFlush(new TextWebSocketFrame(msg + OP_SPLIT + name)));
    }

    private static void noticeAll(String name, String msg) {
        USER_POOL.forEach((k, v) -> k.writeAndFlush(new TextWebSocketFrame(name + OP_SPLIT + msg)));
    }
}
