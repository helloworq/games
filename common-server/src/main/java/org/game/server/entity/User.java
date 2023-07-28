package org.game.server.entity;

import io.netty.channel.Channel;
import lombok.Data;
import org.game.server.operate.enums.UserStatusEnum;

@Data
public class User {
    private String id;
    private String name;
    private String info;
    private int status;
    private Channel channel;//用户关联的网络链接

    public static User createAdmin() {
        User admin = new User();
        admin.setId("0");
        admin.setName("系统管理员");
        admin.setStatus(UserStatusEnum.ONLINE.getCode());
        return admin;
    }
}
