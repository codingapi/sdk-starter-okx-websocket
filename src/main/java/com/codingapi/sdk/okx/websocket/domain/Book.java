package com.codingapi.sdk.okx.websocket.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Book{
    /**
     * 价格
     */
    private double price;

    /**
     * 交易量
     */
    private double instSize;

    /**
     * 订单数量
     */
    private int orderSize;
}
