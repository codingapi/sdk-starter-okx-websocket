package com.codingapi.sdk.okx.websocket.protocol.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreateOrderAnswer {

    /**
     * 由用户设置的订单ID
     */
    private String clOrdId;
    /**
     * 订单ID
     */
    private String ordId;
    /**
     * 订单标签
     */
    private String tag;
    /**
     * 订单状态码，0 代表成功
     */
    private int sCode;
    /**
     * 订单状态消息
     */
    private String sMsg;

    public boolean isSuccess(){
        return sCode==0;
    }
}
