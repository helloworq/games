package org.game.client.operate.chat;

import com.alibaba.fastjson2.JSON;
import org.game.api.entity.Message;

public class OperateChat {

    public static void showInWindow(Message message) {
        System.out.println(JSON.toJSONString(message));
    }
}
