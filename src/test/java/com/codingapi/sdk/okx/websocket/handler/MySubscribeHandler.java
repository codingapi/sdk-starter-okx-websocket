package com.codingapi.sdk.okx.websocket.handler;

import com.codingapi.sdk.okx.websocket.event.ServerConnectedEvent;
import com.codingapi.sdk.okx.websocket.protocol.command.SubscribeCommand;
import com.codingapi.sdk.okx.websocket.sender.PublicCommandPusher;
import com.codingapi.sdk.okx.websocket.sender.TriggerMessage;
import com.codingapi.springboot.framework.event.IHandler;
import com.codingapi.springboot.framework.trigger.TriggerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MySubscribeHandler implements IHandler<ServerConnectedEvent> {

    private static class MyTradeTrigger implements TriggerHandler<TriggerMessage> {
        @Override
        public boolean preTrigger(TriggerMessage trigger) {
            //触发器进入的条件判断
            return trigger.isAction("channel","trades");
        }

        @Override
        public void trigger(TriggerMessage trigger) {
            //触发以后的执行逻辑
            log.info("trades:{}",trigger.getMsg());
        }

        @Override
        public boolean remove(TriggerMessage trigger, boolean canTrigger) {
            //仅当执行过程Trigger才会被移除
            return canTrigger;
        }
    }

    private final SubscribeCommand tradeCommand = new SubscribeCommand("trades","BTC-USDT");

    private final MyTradeTrigger tradeTrigger = new MyTradeTrigger();


    @Override
    public void handler(ServerConnectedEvent event) {
        //是否私有通道
        if(!event.isPrivateChannel()) {
            // tradeCommand 为发送的数据包
            // tradeTrigger 是消息返回以后触发的触发器
            PublicCommandPusher.send(tradeCommand, tradeTrigger);
        }
    }
}
