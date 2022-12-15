package org.game.plane.event;

import com.alibaba.fastjson2.JSON;
import io.netty.channel.Channel;
import io.netty.util.internal.StringUtil;
import org.game.plane.frame.PlaneClient;
import org.game.plane.constans.Config;
import org.game.plane.constans.Direction;
import org.game.plane.constans.Operation;
import org.game.plane.log.LogServer;
import org.game.plane.common.planes.Plane;
import org.game.plane.server.client.ClientMsgCenter;
import org.game.plane.server.client.WebSocketClient;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

//键盘控制
public class KeyMointer extends KeyAdapter {
    private static final int PRESSED = 1;
    private static final int RELEASED = 0;
    private static KeyMointer keyMointer;
    private PlaneClient planeClient;
    private Channel channel;
    private static int rotate = 0;//角度
    //将所有按键事件通过标志位处理
    public static final ConcurrentHashMap<String, Integer> keyMap = new ConcurrentHashMap<>();

    static {
        keyMap.put("w", RELEASED);
        keyMap.put("a", RELEASED);
        keyMap.put("s", RELEASED);
        keyMap.put("d", RELEASED);
        keyMap.put("j", RELEASED);
        keyMap.put("k", RELEASED);
    }

    private KeyMointer() {
    }

    public static Integer getKeyMap(String key) {
        return keyMap.getOrDefault(key, RELEASED);
    }

    public synchronized static KeyMointer getInstance(PlaneClient... planeClient) {
        if (keyMointer == null) {
            keyMointer = new KeyMointer();
            keyMointer.planeClient = planeClient[0];
        }
        return keyMointer;
    }

    /**
     * 所有按键事件都不在本地处理，交由网络模块发送至服务器
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 27) {
            popUpSelection();
        }
        ClientMsgCenter.sendOperate(channel
                , Config.KEY_MAP.getOrDefault(e.getKeyChar(), StringUtil.EMPTY_STRING)
                        + "-"
                        + PRESSED
        );
    }

    public void keyReleased(KeyEvent e) {
        ClientMsgCenter.sendOperate(channel
                , Config.KEY_MAP.getOrDefault(e.getKeyChar(), StringUtil.EMPTY_STRING)
                        + "-"
                        + RELEASED
        );
    }

    public void keyPress(String key, String name) {
        Plane plane = PlaneClient.getPlane(name);
        if (Objects.isNull(plane)) {
            LogServer.add("未找到飞机" + name);
            return;
        }
        //根据键入的数据改变蛇方向控制符
        switch (key) {
            case "w":
                keyMap.put("w" + name, PRESSED);
                break;
            case "a":
                keyMap.put("a" + name, PRESSED);
                break;
            case "s":
                keyMap.put("s" + name, PRESSED);
                break;
            case "d":
                keyMap.put("d" + name, PRESSED);
                break;
            case "j":
                keyMap.put("j" + name, PRESSED);
                break;
            case "k":
                keyMap.put("k" + name, PRESSED);
                break;
        }
    }

    public void keyRelease(String key, String name) {
        Plane plane = PlaneClient.getPlane(name);
        if (Objects.isNull(plane)) {
            LogServer.add("未找到飞机" + name);
            return;
        }
        //根据键入的数据改变蛇方向控制符
        switch (key) {
            case "w":
                keyMap.remove("w" + name);
                break;
            case "a":
                keyMap.remove("a" + name);
                break;
            case "s":
                keyMap.remove("s" + name);
                break;
            case "d":
                keyMap.remove("d" + name);
                break;
            case "j":
                keyMap.remove("j" + name);
                break;
            case "k":
                keyMap.remove("k" + name);
                break;
        }
    }

    private void popUpSelection() {
        // 显示输入对话框, 返回选择的内容, 点击取消或关闭, 则返回null
        //任何输入都不允许有-号
        String inputContent = JOptionPane.showInputDialog(
                planeClient,
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
        Plane plane = new Plane(planeClient.StartPositionX, planeClient.StartPositionY, inputContent);
        //客户端维护生成的飞机
        planeClient.addPlane(plane);
        //将客户端生成的飞机推送至服务器，并由服务器维护已有的飞机列表，待其他客户端连接时可将状态同步过去
        ClientMsgCenter.createPlane(channel, JSON.toJSONString(plane));
    }
}
