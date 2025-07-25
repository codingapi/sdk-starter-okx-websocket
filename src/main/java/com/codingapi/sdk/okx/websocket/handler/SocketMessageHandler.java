package com.codingapi.sdk.okx.websocket.handler;

import com.codingapi.sdk.okx.websocket.event.MessageEvent;
import com.codingapi.sdk.okx.websocket.properties.PropertyContext;
import com.codingapi.springboot.framework.event.IHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocketMessageHandler implements IHandler<MessageEvent> {

    @Override
    public void handler(MessageEvent event) {
        if(PropertyContext.getInstance().isShowMessage()) {
            log.info("message:{}", event.getMsg());
        }
    }
}
