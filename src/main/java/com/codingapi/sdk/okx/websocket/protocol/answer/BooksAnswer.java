package com.codingapi.sdk.okx.websocket.protocol.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class BooksAnswer {

    /**
     * 卖方深度
     */
    private List<List<String>> asks;

    /**
     * 买方深度
     */
    private List<List<String>> bids;

    /**
     * 深度产生的时间
     */
    private long ts;

    /**
     * 数据校验
     */
    private int checksum;

    /**
     * 接受时间
     */
    private long localTs;


    public long getTsDiff() {
        return (localTs - ts);
    }

}
