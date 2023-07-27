package org.game.plane.server.msgbroker;

import org.game.plane.server.msgbroker.entity.Message;

/**
 * 将客户端/服务端传过来的数据统一进行分发
 */
public class Dispatcher {

    private <T extends Message<T>> void doHandle(T t) {
        switch (t.getMsgType()) {
            case CHAT:
                ;
            case LOGIN:
                ;
            case LOGOUT:
                ;
            case OPERATION:
                ;
        }
    }
}
