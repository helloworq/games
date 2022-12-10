package org.game.plane.event;

import org.game.plane.PlaneClient;
import org.game.plane.constans.Direction;
import org.game.plane.constans.Operation;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

//键盘控制
public class KeyMointer extends KeyAdapter {
    private final JFrame jFrame;

    public KeyMointer(JFrame frame) {
        this.jFrame = frame;
    }

    public void keyPressed(KeyEvent e) {
        keyPress(e);
    }

    public void keyPress(KeyEvent e) {
        //根据键入的数据改变蛇方向控制符
        Direction direction = Direction.getByChar(e.getKeyChar());
        System.out.println(e.getKeyCode());
        if (Objects.nonNull(direction)) {
            handleMove(direction);
            return;
        }
        Operation operation = Operation.getByChar(e.getKeyChar());
        if (Objects.nonNull(operation)) {
            handleShoot(operation);
            return;
        }

        if (e.getKeyCode() == 27) {
            popUpSelection();
        }
    }

    private void handleMove(Direction direction) {
        switch (direction) {
            case UP:
                PlaneClient.plane.moveUp();
                break;
            case LEFT:
                PlaneClient.plane.moveLeft();
                break;
            case DOWN:
                PlaneClient.plane.moveDown();
                break;
            case RIGHT:
                PlaneClient.plane.moveRight();
                break;
        }
    }

    private void handleShoot(Operation operation) {
        switch (operation) {
            case SHOOT:
                PlaneClient.plane.shoot();
                break;
            case ATTACK:
                PlaneClient.plane.attack();
                break;
        }
    }

    private void popUpSelection() {
        // 选项按钮
        Object[] options = new Object[]{"联机", "在线", "关于"};

        // 显示选项对话框, 返回选择的选项索引, 点击关闭按钮返回-1
        int optionSelected = JOptionPane.showOptionDialog(
                jFrame,
                "选择",
                "菜单",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,    // 如果传null, 则按钮为 optionType 类型所表示的按钮（也就是确认对话框）
                null
        );

        if (optionSelected >= 0) {
            Object[] second = new Object[]{"联机", "在线", "关于"};
            System.out.println("点击的按钮: " + options[optionSelected]);
            JOptionPane.showOptionDialog(
                    jFrame,
                    "选择",
                    "菜单",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    second,    // 如果传null, 则按钮为 optionType 类型所表示的按钮（也就是确认对话框）
                    null
            );
        }
    }
}
