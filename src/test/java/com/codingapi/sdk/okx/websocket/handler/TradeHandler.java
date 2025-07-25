package com.codingapi.sdk.okx.websocket.handler;

import com.codingapi.sdk.okx.websocket.event.MessageEvent;
import com.codingapi.sdk.okx.websocket.protocol.answer.TradeAnswer;
import com.codingapi.springboot.framework.event.IHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TradeHandler implements IHandler<MessageEvent> {

    @Override
    public void handler(MessageEvent event) {
        if (event.isAction("channel", "trades")) {
            List<TradeAnswer> trades = event.getData(TradeAnswer.class);
            if (trades != null && !trades.isEmpty()) {
                trades.forEach(tradeAnswer -> {
                    tradeAnswer.setLocalTs(event.getTs());
                    log.info("trade ts diff: {}", tradeAnswer.getTsDiff());
                });
            }
        }
    }
}
