package org.game.plane.server.msgbroker.status;

import com.alibaba.fastjson2.JSON;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.game.plane.common.planes.Plane;
import org.game.plane.server.msgbroker.entity.Message;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 保存各种状态数据如已连接用户，现有的已生成飞机
 * 提供有关状态容器的操作方法，其他方法只能调用已提供的方法
 */
public class StatusKeeper {
    //状态存储
    private static final Map<Channel, String> USER_POOL = new ConcurrentHashMap<>();
    private static final List<Plane> PLANE_LIST = new CopyOnWriteArrayList<>();
    //用户
    public static final String ALL = "公告: ";
    //操作符前缀
    public static final String OP_SPLIT = "-";

    public static String getUser(Channel channel) {
        return USER_POOL.get(channel);
    }

    public static void addUser(Channel channel, String name) {
        USER_POOL.put(channel, name);
    }

    public static boolean userExists(String name) {
        return USER_POOL.values().stream().anyMatch(e -> e.equals(name));
    }

    public static void removeUser(Channel channel) {
        String id = USER_POOL.get(channel);
        PLANE_LIST.removeIf(e -> e.getId().equals(id));
        USER_POOL.remove(channel);
    }

    public static void notice(Message message) {
        if (message.isSend2All()) {
            notice2All(message);
        } else {
            notice2Single(message);
        }
    }

    //广播至全体客户端-不区分系统广播和用户广播
    private static void notice2All(Message message) {
        message.setSend2All(true);
        USER_POOL.forEach((k, v) -> k.writeAndFlush(wrapData(message)));
    }

    //广播至指定客户端-不区分系统广播和用户广播
    private static void notice2Single(Message message) {
        message.setSend2All(false);
        USER_POOL.forEach((k, v) -> {
            if (v.equals(message.getReceiver())) {
                k.writeAndFlush(wrapData(message));
            }
        });
    }

    private static TextWebSocketFrame wrapData(Message message) {
        return new TextWebSocketFrame(JSON.toJSONString(message));
    }
}
