/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.game.plane.server.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.internal.StringUtil;

import java.util.UUID;

/**
 * Echoes uppercase content of text frames.
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ServerMsgCenter.addUser(ctx.channel(), UUID.randomUUID().toString());
        ServerMsgCenter.execIfDec(ctx.channel(), ServerMsgCenter.MSG_SYSTEM
                + ServerMsgCenter.OP_SPLIT
                + "有人上线啦!");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ServerMsgCenter.removeUser(ctx.channel());
        System.out.println("用户退出");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled
        if (frame instanceof TextWebSocketFrame) {
            // Send the uppercase string back.
            String request = ((TextWebSocketFrame) frame).text();
            ServerMsgCenter.execIfDec(ctx.channel(), request);
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }
}
