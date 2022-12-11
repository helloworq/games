package org.game.plane.log;

import java.util.LinkedList;

/**
 * 此类用于存储客户端收到的信息，并提供给客户端显示在画面上
 */
public class LogServer {
    private static final LinkedList<String> MESSAGE = new LinkedList<>();
    private static final Long start = System.currentTimeMillis();

    public synchronized static String get() {
        try {
            if ((System.currentTimeMillis() - start) > 20000 && !MESSAGE.isEmpty()) {
                MESSAGE.removeFirst();
            }
            return MESSAGE.getFirst();
        } catch (Exception e) {
            return null;
        }
    }

    public static void add(String msg) {
        MESSAGE.addLast(msg);
    }

    public static void remove(String msg) {
        MESSAGE.remove(msg);
    }
}
