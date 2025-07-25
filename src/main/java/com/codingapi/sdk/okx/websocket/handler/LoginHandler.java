package com.codingapi.sdk.okx.websocket.handler;

import com.codingapi.sdk.okx.websocket.event.LoginSucceededEvent;
import com.codingapi.sdk.okx.websocket.event.ServerConnectedEvent;
import com.codingapi.sdk.okx.websocket.properties.OkxSocketConfigProperties;
import com.codingapi.sdk.okx.websocket.protocol.command.LoginCommand;
import com.codingapi.sdk.okx.websocket.sender.PrivateCommandPusher;
import com.codingapi.sdk.okx.websocket.sender.PublicCommandPusher;
import com.codingapi.sdk.okx.websocket.sender.TriggerMessage;
import com.codingapi.springboot.framework.event.EventPusher;
import com.codingapi.springboot.framework.event.IHandler;
import com.codingapi.springboot.framework.trigger.TriggerHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class LoginHandler implements IHandler<ServerConnectedEvent> {

    private final OkxSocketConfigProperties properties;

    @SneakyThrows
    @Override
    public void handler(ServerConnectedEvent event) {
        if(event.isPublicChannel()){
            PublicCommandPusher.getInstance().setActive(true);
        }
        if(event.isPrivateChannel()) {
            LoginCommand command = new LoginCommand(properties);
            PrivateCommandPusher.send(command, new TriggerHandler<TriggerMessage>() {
                @Override
                public boolean preTrigger(TriggerMessage event) {
                    if (event.containsKey("event") && event.containsKey("code")) {
                        if ("login".equals(event.getString("event"))) {
                            return "0".equals(event.getString("code"));
                        }
                    }
                    return false;
                }

                @Override
                public void trigger(TriggerMessage triggerMessage) {
                    PrivateCommandPusher.getInstance().setActive(true);
                    EventPusher.push(new LoginSucceededEvent());
                }
            });
        }
    }
}
