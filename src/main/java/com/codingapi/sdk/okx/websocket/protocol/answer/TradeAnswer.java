package com.codingapi.sdk.okx.websocket.protocol.answer;

import lombok.Getter;
import lombok.Setter;

/**
 * 订阅交易记录数据推送事件
 */
@Setter
@Getter
public class TradeAnswer {

    /**
     * 产品名称
     */
    private String instId;
    /**
     * 方向
     */
    private String side;
    /**
     * 成交数量
     */
    private float sz;
    /**
     * 成交价格
     */
    private float px;
    /**
     * 交易id
     */
    private long tradeId;
    /**
     * 成交时间
     */
    private long ts;
    /**
     * 本地接受时间
     */
    private long localTs;


    public boolean isBuy() {
        return "buy".equals(side);
    }

    public boolean isSell() {
        return "sell".equals(side);
    }

    public long getTsDiff() {
        return (localTs - ts);
    }

    @Override
    public String toString(){
        return "TradeAnswer{" +
                "instId='" + instId + '\'' +
                ", side='" + side + '\'' +
                ", sz=" + sz +
                ", px=" + px +
                ", tradeId=" + tradeId +
                ", ts=" + ts +
                ", localTs=" + localTs +
                ", tsDiff=" + getTsDiff() +
                '}';
    }

}
