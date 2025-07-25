package com.codingapi.sdk.okx.websocket.dto.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderAlgo {

    /**
     * 策略委托单ID
     */
    private String algoId;
    /**
     * 止损触发价
     */
    private float slTriggerPx;
    /**
     * 止损触发价类型
     * last：最新价格
     * index：指数价格
     * mark：标记价格
     */
    private String slTriggerPxType;
    /**
     * 止盈委托价
     */
    private float tpTriggerPx;
    /**
     * 止盈触发价类型
     * last：最新价格
     * index：指数价格
     * mark：标记价格
     */
    private String tpTriggerPxType;
    /**
     * 策略委托触发时，平仓的百分比。1 代表100%
     */
    private float closeFraction;

}
