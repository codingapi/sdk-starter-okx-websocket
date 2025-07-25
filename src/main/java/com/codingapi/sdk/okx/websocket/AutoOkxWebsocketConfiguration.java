package com.codingapi.sdk.okx.websocket;

import com.codingapi.sdk.okx.websocket.event.LoginSucceededEvent;
import com.codingapi.sdk.okx.websocket.handler.LoginHandler;
import com.codingapi.sdk.okx.websocket.handler.SocketMessageHandler;
import com.codingapi.sdk.okx.websocket.properties.OkxSocketConfigProperties;
import com.codingapi.sdk.okx.websocket.properties.OkxSocketProtocolProperties;
import com.codingapi.sdk.okx.websocket.socket.WebSocketRunner;
import com.codingapi.springboot.framework.event.IHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;

@Configuration
public class AutoOkxWebsocketConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "codingapi.okx.config")
    public OkxSocketConfigProperties okxSocketConfigProperties() {
        return new OkxSocketConfigProperties();
    }


    @Bean
    @ConfigurationProperties(prefix = "codingapi.okx.socket")
    public OkxSocketProtocolProperties okxSocketProtocolProperties() {
        return new OkxSocketProtocolProperties();
    }

    @Bean
    public WebSocketRunner webSocketRunner(OkxSocketProtocolProperties properties,
                                           OkxSocketConfigProperties okxSocketProperties) throws URISyntaxException {
        return new WebSocketRunner(properties, okxSocketProperties);
    }

    @Bean
    public LoginHandler loginHandler(OkxSocketConfigProperties okxSocketConfigProperties){
        return new LoginHandler(okxSocketConfigProperties);
    }

    @Bean
    public IHandler<LoginSucceededEvent> loginSucceededEventIHandler(){

        return new IHandler<LoginSucceededEvent>() {

            private final Logger logger = LoggerFactory.getLogger(getClass());

            @Override
            public void handler(LoginSucceededEvent event) {
                logger.info("login succeeded ");
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public SocketMessageHandler socketMessageHandler(){
        return new SocketMessageHandler();
    }
}
