package org.game.client;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.game.client.connect.WebSocketClient;

import javax.net.ssl.SSLException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) throws URISyntaxException, InterruptedException, SSLException {
        Channel channel = WebSocketClient.connect();

        Scanner s = new Scanner(System.in);

        while (true) {
            String line = s.nextLine();
            channel.writeAndFlush(new TextWebSocketFrame(line));
        }
    }
}
