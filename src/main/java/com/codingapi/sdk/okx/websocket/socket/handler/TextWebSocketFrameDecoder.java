package com.codingapi.sdk.okx.websocket.socket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

public class TextWebSocketFrameDecoder extends MessageToMessageDecoder<TextWebSocketFrame> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          TextWebSocketFrame textWebSocketFrame,
                          List<Object> list) throws Exception {
        list.add(textWebSocketFrame.text());
    }
}
