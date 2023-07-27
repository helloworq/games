package org.game.plane.server.msgbroker.entity;

import lombok.Data;
import org.game.plane.server.msgbroker.Type;

/**
 * 全部信息传递类的父类
 * 服务器发送消息可能有三种情况:广播全部客户端，单独通知某个客户端，通知某组客户端。目前只考虑前两种情况
 */
@Data
public class Message<T> {
    private String sender;
    private String receiver;
    private T body;
    private Long timeStamp;
    private Type.MsgType msgType;
    private String name;
    private boolean send2All;//true -> 不取receiver的值。false -> 则获取receiver
    private String msg;
    private String operateFlag;//操作标记，根据此标记交由不同的处理器处理
}