package org.game.server.entity;

import lombok.Data;

@Data
public class Message {
    private String MsgId;

    private String senderId;
    private String senderName;
    private String receiverId;
    private String receiverName;
    private int operateCode;//操作类型，通过code区分
    private int sendType;//发送方式 -> 单独/群发
    private String body;
}
