package org.game.server.pools;

import com.alibaba.fastjson2.JSON;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.game.api.entity.Message;
import org.game.api.entity.User;
import org.game.api.operate.enums.UserStatusEnum;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 保存用户相关的状态信息，对用户的操作只能由该类提供的方法
 */
public class UserPools {
    private static final Map<String, User> UserMap = new LinkedHashMap<>();

    static {
        UserMap.put("0", User.createAdmin());
    }

    public static void register(User user) {
        UserMap.put(user.getId(), user);
    }

    public static void offline(String id) {
        UserMap.computeIfPresent(id, (k, v) -> {
            v.setStatus(UserStatusEnum.OFFLINE.getCode());
            return v;
        });
    }

    public static void send2One(Message message) {
        String receiverId = message.getReceiverId();
        User receiver = UserMap.get(receiverId);
        if (Objects.nonNull(receiver)) {
            receiver.getChannel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
        }
    }

    public static void send2All(Message message) {
        UserMap.values().forEach(e -> e.getChannel().writeAndFlush(JSON.toJSONString(message)));
    }

    public static void updateUser(User user) {
        String userId = user.getId();
        UserMap.computeIfPresent(userId, (k, v) -> user);
    }

    public static User getUser(String id) {
        return UserMap.get(id);
    }

    public static User getUserByChannel(Channel c) {
        return UserMap.values().stream().filter(e -> c.equals(e.getChannel())).findFirst().orElse(null);
    }

    public static List<User> getUserList() {
        return new ArrayList<>(UserMap.values());
    }
}
