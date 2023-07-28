package org.game.server.operate.normal;

import com.alibaba.fastjson2.JSON;
import org.game.api.entity.Message;
import org.game.api.entity.User;
import org.game.api.operate.enums.OperateEnum;
import org.game.server.operate.chat.OperateChat;
import org.game.server.pools.UserPools;

import java.util.Objects;

public class OperateUserInfo {

    private static String getUserId(Message message) {
        User user = UserPools.getUserByChannel(message.getChannel());
        if (Objects.nonNull(user)) {
            return user.getId();
        }
        return null;
    }

    public static void changeUsername(Message message) {
        String senderId = getUserId(message);
        User user = UserPools.getUser(senderId);
        if (Objects.nonNull(user)) {
            user.setName(message.getSenderName());
            UserPools.updateUser(user);
        }
    }

    public static void getUserList(Message message) {
        String senderId = getUserId(message);

        message.setOperateCode(OperateEnum.DISPLAY.getCode());
        message.setReceiverId(senderId);
        message.setBody(JSON.toJSONString(UserPools.getUserList()));

        OperateChat.chat(message);
    }
}
