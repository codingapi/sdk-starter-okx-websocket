package com.codingapi.sdk.okx.websocket.socket.handler;

import com.alibaba.fastjson.JSONObject;
import com.codingapi.sdk.okx.websocket.event.MessageEvent;
import com.codingapi.sdk.okx.websocket.event.ServerConnectedEvent;
import com.codingapi.sdk.okx.websocket.sender.PrivateCommandPusher;
import com.codingapi.sdk.okx.websocket.sender.PublicCommandPusher;
import com.codingapi.sdk.okx.websocket.sender.TriggerMessage;
import com.codingapi.springboot.framework.event.EventPusher;
import com.codingapi.springboot.framework.trigger.TriggerContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MyWebSocketClientHandler extends SimpleChannelInboundHandler<String> {

    private final ExecutorService eventPools = Executors.newFixedThreadPool(1);
    private final ExecutorService triggerPools = Executors.newFixedThreadPool(1);

    private final boolean isPrivateChannel;
    public MyWebSocketClientHandler(boolean isPrivateChannel) {
        this.isPrivateChannel = isPrivateChannel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("okx server connected.");
        if(isPrivateChannel) {
            PrivateCommandPusher.getInstance().registerChannel(ctx.channel());
        }else{
            PublicCommandPusher.getInstance().registerChannel(ctx.channel());
        }
        ctx.executor().schedule(() -> {
            EventPusher.push(new ServerConnectedEvent(isPrivateChannel));
        }, 10, TimeUnit.SECONDS);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("okx server disconnected. {}", ctx);
        if(isPrivateChannel) {
            PrivateCommandPusher.getInstance().registerChannel(ctx.channel());
        }else{
            PublicCommandPusher.getInstance().registerChannel(ctx.channel());
        }
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String message) throws Exception {
        log.debug("channelRead msg:{}", message);
        if (JSONObject.isValid(message)) {
            JSONObject jsonObject = JSONObject.parseObject(message);
            triggerPools.execute(()->{
                TriggerContext.getInstance().trigger(new TriggerMessage(jsonObject));
            });
            eventPools.execute(()->{
                EventPusher.push(new MessageEvent(jsonObject));
            });
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            final IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.warn("channel address: {} read timeout close", ctx.channel().remoteAddress());
                ctx.close();
            }

            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush("ping");
            }
        }
    }
}
