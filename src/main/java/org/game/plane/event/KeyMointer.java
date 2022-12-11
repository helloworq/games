package org.game.plane.event;

import com.alibaba.fastjson2.JSON;
import io.netty.channel.Channel;
import org.game.plane.PlaneClient;
import org.game.plane.constans.Config;
import org.game.plane.constans.Direction;
import org.game.plane.constans.Operation;
import org.game.plane.log.LogServer;
import org.game.plane.planes.Plane;
import org.game.plane.server.client.ClientMsgCenter;
import org.game.plane.server.client.WebSocketClient;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

//键盘控制
public class KeyMointer extends KeyAdapter {
    private final JFrame jFrame;
    private Channel channel;

    public KeyMointer(JFrame frame) {
        this.jFrame = frame;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 27) {
            popUpSelection();
        }
        ClientMsgCenter.sendOperate(channel, Config.KEY_MAP.getOrDefault(e.getKeyChar(), null));
    }

    public static void keyPress(String key, String name) {
        Plane plane = PlaneClient.getPlane(name);
        if (Objects.isNull(plane)) {
            LogServer.add("未找到飞机" + name);
            return;
        }
        //根据键入的数据改变蛇方向控制符
        Direction direction = Direction.getByString(key);
        if (Objects.nonNull(direction)) {
            handleMove(direction, plane);
            return;
        }
        Operation operation = Operation.getByString(key);
        if (Objects.nonNull(operation)) {
            handleShoot(operation, plane);
            return;
        }
    }

    private static void handleMove(Direction direction, Plane plane) {
        switch (direction) {
            case UP:
                plane.moveUp();
                break;
            case LEFT:
                plane.moveLeft();
                break;
            case DOWN:
                plane.moveDown();
                break;
            case RIGHT:
                plane.moveRight();
                break;
        }
    }

    private static void handleShoot(Operation operation, Plane plane) {
        switch (operation) {
            case SHOOT:
                plane.shoot();
                break;
            case ATTACK:
                plane.attack();
                break;
        }
    }

    private void popUpSelection() {
        // 显示输入对话框, 返回选择的内容, 点击取消或关闭, 则返回null
        //任何输入都不允许有-号
        String inputContent = JOptionPane.showInputDialog(
                jFrame,
                "输入你的联机名字:",
                System.currentTimeMillis()
        );
        try {
            //尝试连接服务器
            channel = WebSocketClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //更改服务器上本机的名称
        ClientMsgCenter.changeName(channel, inputContent);
        //根据现有参数生成飞机
        Plane plane = new Plane(PlaneClient.StartPositionX, PlaneClient.StartPositionY, Direction.UP, inputContent);
        //客户端维护生成的飞机
        PlaneClient.addPlane(plane);
        //将客户端生成的飞机推送至服务器，并由服务器维护已有的飞机列表，待其他客户端连接时可将状态同步过去
        ClientMsgCenter.createPlane(channel, JSON.toJSONString(plane));
    }
}
