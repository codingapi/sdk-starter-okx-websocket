[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/codingapi/sdk-starter-okx-websocket/blob/main/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.codingapi.sdk/starter-okx-websocket.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.codingapi.sdk%22%20AND%20a:%22starter-okx-websocket%22)

# sdk-starter-okx-websocket

基于OKX v5 websocket api开发的springboot的OKX-websocket-SDK starter  
[OKX API](https://www.okx.com/docs-v5/zh/#websocket-api)

websocket 将会建立两个链接，分别是私有通道与共有通道  
目前框架再启动以后会推送两种事件：  
1. ServerConnectedEvent
2. LoginSucceededEvent

ServerConnectedEvent是无论共有还是私有都会推送的事件，因此在订阅的时候注意区分。可通过event.isPrivateChannel()判断。  
LoginSucceededEvent是尽在私有通道下才会推送的事件。  

## 使用步骤

* maven依赖
```
    <dependency>
        <groupId>com.codingapi.sdk</groupId>
        <artifactId>starter-okx-websocket</artifactId>
        <version>${last.version}</version>
    </dependency>
```
* 配置OKX APIKEY
```

# OKX api 参数配置

codingapi.okx.config.apikey=xxx
codingapi.okx.config.secret-key=xxx
codingapi.okx.config.passphrase=xxx
codingapi.okx.config.mock=true
codingapi.okx.config.socket-private-url=wss://wspap.okx.com:8443/ws/v5/private?brokerId=9999
codingapi.okx.config.socket-public-url=wss://wspap.okx.com:8443/ws/v5/public?brokerId=9999

```

* SDK使用 
```

import com.codingapi.sdk.okx.websocket.event.ServerConnectedEvent;
import com.codingapi.sdk.okx.websocket.protocol.command.SubscribeCommand;
import com.codingapi.sdk.okx.websocket.sender.PublicCommandPusher;
import com.codingapi.sdk.okx.websocket.sender.TriggerMessage;
import com.codingapi.springboot.framework.handler.IHandler;
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

```
