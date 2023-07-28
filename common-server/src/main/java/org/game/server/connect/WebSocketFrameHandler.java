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
package org.game.server.connect;

import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.game.api.entity.Message;
import org.game.api.entity.User;
import org.game.server.operate.Dispatcher;
import org.game.server.pools.UserPools;

/**
 * Echoes uppercase content of text frames.
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //初次链接到服务器
        String id = ctx.channel().id().asShortText();
        Channel channel = ctx.channel();

        User user = new User();
        user.setId(id);
        user.setChannel(channel);

        UserPools.register(user);

        System.out.println("链接");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String id = ctx.channel().id().asShortText();
        UserPools.offline(id);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof TextWebSocketFrame) {
            String request = ((TextWebSocketFrame) frame).text();

            Message message = JSONObject.parseObject(request, Message.class);
            message.setChannel(ctx.channel());
            Dispatcher.doDispatcher(message);
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }
}