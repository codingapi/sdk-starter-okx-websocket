package com.codingapi.sdk.okx.websocket.sender;

import com.codingapi.sdk.okx.websocket.protocol.command.ICommand;
import com.codingapi.springboot.framework.trigger.TriggerHandler;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 公共频道的推手
 */
@Slf4j
public final class PublicCommandPusher {

    private final static PublicCommandPusher instance = new PublicCommandPusher();

    private final ChannelGroup channelGroup;

    @Getter
    @Setter
    private boolean active;

    private PublicCommandPusher(){
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    public static PublicCommandPusher getInstance() {
        return instance;
    }

    public void registerChannel(Channel channel) {
        channelGroup.add(channel);
    }

    public void removeChannel(Channel channel) {
        channelGroup.remove(channel);
    }

    public static void send(ICommand command) {
        CommandSender.send(getInstance().channelGroup,command,null);
    }

    public static void send(ICommand command,
                            TriggerHandler<TriggerMessage> eventTrigger){
        CommandSender.send(getInstance().channelGroup,command,eventTrigger);
    }



}
