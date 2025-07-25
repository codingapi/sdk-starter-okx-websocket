package com.codingapi.sdk.okx.websocket.protocol.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InterestAnswer {

    /**
     * 产品类型
     */
    private String instType;
    /**
     * 产品名称
     */
    private String instId;
    /**
     * 持仓量 张
     */
    private float oi;
    /**
     * 持仓量 币
     */
    private float oiCcy;
    /**
     * 仓位更新时间
     */
    private long ts;

    /**
     * 接受时间
     */
    private long localTs;
}
