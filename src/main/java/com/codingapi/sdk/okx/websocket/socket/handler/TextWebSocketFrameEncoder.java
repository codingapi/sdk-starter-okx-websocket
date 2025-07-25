package com.codingapi.sdk.okx.websocket.socket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

public class TextWebSocketFrameEncoder extends MessageToMessageEncoder<String> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          String content,
                          List<Object> list) throws Exception {
        list.add(new TextWebSocketFrame(content));
    }
}
