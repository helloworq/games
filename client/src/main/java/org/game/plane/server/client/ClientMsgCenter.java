package org.game.plane.server.client;

import com.alibaba.fastjson2.JSON;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;
import org.game.plane.frame.PlaneClient;
import org.game.plane.event.KeyMointer;
import org.game.plane.log.LogServer;
import org.game.plane.common.planes.Plane;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 接收并处理来自服务端消息
 */
public class ClientMsgCenter {
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
            Map.of(MSG_CHANGE_NAME, (channel, msg) -> channel.writeAndFlush(new TextWebSocketFrame(msg)),
                    OPERATE, (channel, msg) -> channel.writeAndFlush(new TextWebSocketFrame(msg)),
                    //MSG_SYSTEM, (channel, msg) -> System.out.println(ALL + msg.split(OP_SPLIT)[1]),
                    MSG_SYSTEM, (channel, msg) -> LogServer.add(ALL + msg.split(OP_SPLIT)[1]),
                    //MSG_USER, (channel, msg) -> System.out.println(msg.split(OP_SPLIT)[2] + SPEAK + msg.split(OP_SPLIT)[1]),
                    MSG_USER, (channel, msg) -> LogServer.add(msg.split(OP_SPLIT)[2] + SPEAK + msg.split(OP_SPLIT)[1]),
                    MSG_OPERATE, (channel, msg) -> receiveOperate(channel, msg),
                    NEW_PLANE, (channel, msg) -> channel.writeAndFlush(new TextWebSocketFrame(msg)),
                    PLANE_LIST_INFO, (channel, msg) -> getPlaneList(channel, msg));

    public static void changeName(Channel channel, String msg) {
        msg = MSG_CHANGE_NAME + OP_SPLIT + msg;
        execIfDec(channel, msg);
    }

    public static void receiveOperate(Channel channel, String msg) {
        String[] args = msg.split(OP_SPLIT);
        String id = args[3];
        String order = args[1];
        String isPressed = args[2];
        if (1 == Integer.parseInt(isPressed)) {
            KeyMointer.getInstance().keyPress(order, id);
        } else {
            KeyMointer.getInstance().keyRelease(order, id);
        }
    }

    public static void sendOperate(Channel channel, String msg) {
        msg = OPERATE + OP_SPLIT + msg;
        execIfDec(channel, msg);
    }

    public static void createPlane(Channel channel, String msg) {
        msg = NEW_PLANE + OP_SPLIT + msg;
        execIfDec(channel, msg);
    }

    //将服务器中的飞机列表与本地列表同步
    public static void getPlaneList(Channel channel, String msg) {
        try {
            String[] args = msg.split(OP_SPLIT);
            List<Plane> planeList = JSON.parseArray(args[1], Plane.class);
            PlaneClient.addPlaneList(planeList);
        } catch (Exception e) {
            System.out.println("写入失败");
        }
    }

    public static void execIfDec(Channel channel, String msg) {
        if (StringUtil.isNullOrEmpty(msg)) {
            return;
        }
        String[] data = msg.split(OP_SPLIT);
        String op = data[0];
        if (data.length <= 1) {
            //非预定义消息不处理
            return;
        }
        if (OperationPrefix.containsKey(op)) {
            OperationPrefix.get(op).accept(channel, msg);
        }
    }
}
