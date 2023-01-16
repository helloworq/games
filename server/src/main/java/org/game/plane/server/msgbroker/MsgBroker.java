package org.game.plane.server.msgbroker;

import io.netty.channel.Channel;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.game.plane.server.msgbroker.entity.Message;
import org.game.plane.server.msgbroker.operate.Operate;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * 消息中转器-接受所有来自客户端的请求并进行处理(重写已有的MsgCenter模块)
 */
public class MsgBroker {
    /**
     * 接受客户端请求并转发到处理器
     *
     * @param channel 会话通道
     * @param msg     消息
     */
    public static void receiveAndForward(Channel channel, Message msg) {
        if (Objects.isNull(msg) || StringUtil.isNullOrEmpty(msg.getMsg())) {
            return;
        }
        String flagKey = msg.getOperateFlag();
        Flag flag = Flag.getByFlag(flagKey);
        if (Objects.nonNull(flag)) {
            flag.getBiConsumer().accept(channel, msg);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Flag {
        MSG_CHANGE_NAME("@@ChangeName", (Operate::changeName)),
        MSG_SYSTEM("@@MsgFromSystem", ((channel, s) -> System.out.println(s))),
        MSG_USER("@@MsgFromUser", ((channel, s) -> System.out.println(s))),
        MSG_OPERATE("@@MsgOperate", ((channel, s) -> System.out.println(s))),
        OPERATE("@@Operate", ((channel, s) -> System.out.println(s))),
        NEW_PLANE("@@NewPlane", ((channel, s) -> System.out.println(s))),
        PLANE_LIST_INFO("@@PlaneListInfo", ((channel, s) -> System.out.println(s)));

        private final String flag;
        private final BiConsumer<Channel, Message> biConsumer;

        public static Flag getByFlag(String flag) {
            return Stream.of(values())
                    .filter(e -> e.flag.equals(flag))
                    .findFirst().orElse(null);
        }
    }
}
