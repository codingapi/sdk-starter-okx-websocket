package com.codingapi.sdk.okx.websocket.sender;

import com.codingapi.sdk.okx.websocket.properties.PropertyContext;
import com.codingapi.sdk.okx.websocket.protocol.command.ICommand;
import com.codingapi.springboot.framework.trigger.TriggerContext;
import com.codingapi.springboot.framework.trigger.TriggerHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class CommandSender {

    public static void send(ChannelGroup channel,ICommand command,
                            TriggerHandler<TriggerMessage> eventTrigger){
        String cmd = command.toCmd();
        if(PropertyContext.getInstance().isShowMessage()) {
            log.info("send:{}",command);
        }
        channel.writeAndFlush(cmd);
        if (eventTrigger != null) {
            TriggerContext.getInstance().addTrigger(eventTrigger);
        }
    }

}
