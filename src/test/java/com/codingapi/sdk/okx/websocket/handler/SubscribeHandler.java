package com.codingapi.sdk.okx.websocket.handler;

import com.codingapi.sdk.okx.websocket.event.ServerConnectedEvent;
import com.codingapi.sdk.okx.websocket.protocol.command.SubscribeCommand;
import com.codingapi.sdk.okx.websocket.sender.PublicCommandPusher;
import com.codingapi.sdk.okx.websocket.trigger.BookTrigger;
import com.codingapi.sdk.okx.websocket.trigger.TradeTrigger;
import com.codingapi.springboot.framework.event.IHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SubscribeHandler implements IHandler<ServerConnectedEvent> {

    private final SubscribeCommand tradeCommand = new SubscribeCommand("trades","BTC-USDT");
    private final SubscribeCommand booksCommand = new SubscribeCommand("books","BTC-USDT");
    private final TradeTrigger tradeTrigger = new TradeTrigger();
    private final BookTrigger bookTrigger = new BookTrigger();

    @Override
    public void handler(ServerConnectedEvent event) {
        //是否私有通道
        if(!event.isPrivateChannel()) {
            // tradeCommand 为发送的数据包
            // tradeTrigger 是消息返回以后触发的触发器
            PublicCommandPusher.send(tradeCommand, tradeTrigger);

            // booksCommand 为发送的数据包
            // bookTrigger 是消息返回以后触发的触发器
            PublicCommandPusher.send(booksCommand, bookTrigger);
        }
    }
}
