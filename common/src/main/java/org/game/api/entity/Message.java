package org.game.api.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class Message {
    private String MsgId;
    @JSONField(serialize = false)
    private Channel channel;

    private String senderId;
    private String senderName;
    private String receiverId;
    private String receiverName;
    private int operateCode;//操作类型，通过code区分
    private int sendType;//发送方式 -> 单独/群发
    private String body;
}
