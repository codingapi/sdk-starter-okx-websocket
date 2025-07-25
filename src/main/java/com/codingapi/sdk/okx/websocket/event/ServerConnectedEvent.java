package com.codingapi.sdk.okx.websocket.event;

import com.codingapi.springboot.framework.event.IEvent;
import lombok.Getter;

/**
 * Socket服务链接成功事件
 */
public class ServerConnectedEvent implements IEvent {

    @Getter
    private final boolean privateChannel;

    public ServerConnectedEvent(boolean privateChannel) {
        this.privateChannel = privateChannel;
    }

    public boolean isPublicChannel(){
        return !privateChannel;
    }
}
