package com.codingapi.sdk.okx.websocket.protocol.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderCreateCommandTest {

    @Test
    void toCmd() {
        OrderCreateCommand command = new OrderCreateCommand();
        OrderCreateCommand.Order order = OrderCreateCommand.Order.builder()
                .clOrdId("123")
                .sz("100")
                .tdMode("isolated")
                .tdMode("market")
                .side("buy")
                .build();
        command.addOrder(order);
        String cmd = command.toCmd();
        assertNotNull(cmd);
        assertFalse(cmd.contains("reduceOnly"));
        System.out.println(cmd);
    }
}