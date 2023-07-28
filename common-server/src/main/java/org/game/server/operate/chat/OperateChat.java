package org.game.server.operate.chat;

import org.game.api.entity.Message;
import org.game.api.operate.enums.SendTypeEnum;
import org.game.server.pools.UserPools;

public class OperateChat {

    public static void chat(Message message) {
        int sendType = message.getSendType();
        if (SendTypeEnum.ONE_2_ONE.getCode().equals(sendType)) {
            UserPools.send2One(message);
        } else if (SendTypeEnum.TO_ALL.getCode().equals(sendType)) {
            UserPools.send2All(message);
        }
    }
}
