package com.codingapi.sdk.okx.websocket.socket;

import com.codingapi.sdk.okx.websocket.properties.OkxSocketConfigProperties;
import com.codingapi.sdk.okx.websocket.properties.OkxSocketProtocolProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class WebSocketRunner implements ApplicationRunner, DisposableBean {

    private final WebSocketClient privateClient;
    private final WebSocketClient publicClient;

    private final ExecutorService threadPools = Executors.newFixedThreadPool(2);

    public WebSocketRunner(OkxSocketProtocolProperties properties,
                           OkxSocketConfigProperties okxConfig) throws URISyntaxException {
        this.privateClient = new WebSocketClient(okxConfig.getSocketPrivateUrl(),true,properties);
        this.publicClient = new WebSocketClient(okxConfig.getSocketPublicUrl(),false,properties);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("client connect okx server.... ");
        threadPools.execute(privateClient::connect);
        threadPools.execute(publicClient::connect);
    }


    @Override
    public void destroy() throws Exception {
        log.info("client close okx server. ");
        privateClient.close();
        publicClient.close();
    }
}
