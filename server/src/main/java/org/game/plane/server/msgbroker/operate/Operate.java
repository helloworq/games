package org.game.plane.server.msgbroker.operate;

import io.netty.channel.Channel;
import io.netty.util.internal.StringUtil;
import org.game.plane.server.msgbroker.entity.Message;
import org.game.plane.server.msgbroker.status.StatusKeeper;

/**
 * 消息处理器，处理来自中转器的消息
 */
public class Operate {
    public static void changeName(Channel channel, Message msg) {
        String newName = msg.getName();
        if (StatusKeeper.userExists(newName)) {

        }

        StatusKeeper.addUser(channel, newName);
    }

}
