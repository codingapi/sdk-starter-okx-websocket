package com.codingapi.sdk.okx.websocket.protocol.command;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 下单
 * <a href="https://www.okx.com/docs-v5/zh/#websocket-api-trade-place-order">api</a>
 */
@ToString
public class OrderCreateCommand implements ICommand{

    /**
     * 消息的唯一标识
     */
    @Getter
    private final String id;

    @Getter
    private final List<Order> arrayList = new ArrayList<>();

    /**
     * 交易时效性
     * 请求有效截止时间。Unix时间戳的毫秒数格式，如 1597026383085
     *
     * 由于网络延时或者OKX服务器繁忙会导致订单无法及时处理。如果您对交易时效性有较高的要求，可以灵活设置请求有效截止时间expTime以达到你的要求。
     * （批量）下单，（批量）改单接口请求中如果包含expTime，如果服务器当前系统时间超过expTime，则该请求不会被服务器处理。你应跟我们服务器系统时间同步。请用获取系统时间来获取系统时间。
     */
    @Getter
    @Setter
    private String expTime;

    public OrderCreateCommand() {
        this.id = String.valueOf(System.currentTimeMillis());
    }

    @Override
    public String toCmd() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("op", "order");
        jsonObject.put("id", id);
        if(StringUtils.hasText(expTime)){
            jsonObject.put("expTime", expTime);
        }
        jsonObject.put("args", arrayList);
        return jsonObject.toJSONString();
    }

    public void addOrder(Order order){
        this.arrayList.add(order);
    }

    @Setter
    @Getter
    @ToString
    @Builder
    public static class Order{
        /**
         * 订单方向，buy sell
         */
        private String side;
        /**
         * 产品ID，如 BTC-USD
         */
        private String instId;
        /**
         * 交易模式
         * 保证金模式 isolated：逐仓 cross： 全仓
         * 非保证金模式 cash：现金
         */
        private String tdMode;

        /**
         * 保证金币种，仅适用于单币种保证金账户下的全仓杠杆订单
         */
        private String ccy;

        /**
         * 由用户设置的订单ID
         * 字母（区分大小写）与数字的组合，可以是纯字母、纯数字且长度要在1-32位之间。
         */
        private String clOrdId;

        /**
         * 订单标签
         * 字母（区分大小写）与数字的组合，可以是纯字母、纯数字且长度要在1-16位之间。
         */
        private String tag;


        /**
         * 持仓方向
         * 在单向持仓模式下，默认 net
         * 在双向持仓模式下必填，且仅可选择 long 或 short，仅适用于交割/永续
         */
        private String posSide;


        /**
         * 订单类型
         * market：市价单
         * limit：限价单
         * post_only：只做maker单
         * fok：全部成交或立即取消
         * ioc：立即成交并取消剩余
         * optimal_limit_ioc：市价委托立即成交并取消剩余（仅适用交割、永续）
         */
        private String ordType;

        /**
         * 委托数量
         */
        private String sz;

        /**
         * 委托价，仅适用于limit、post_only、fok、ioc类型的订单
         */
        private String px;

        /**
         * 是否只减仓，true 或 false，默认false
         * 仅适用于币币杠杆，以及买卖模式下的交割/永续
         * 仅适用于单币种保证金模式和跨币种保证金模式
         */
        private String reduceOnly;


        /**
         * 币币市价单委托数量sz的单位
         * base_ccy: 交易货币 ；quote_ccy：计价货币
         * 仅适用于币币市价订单
         * 默认买单为quote_ccy，卖单为base_ccy
         */
        private String tgtCcy;

        /**
         * 是否禁止币币市价改单，true 或 false，默认false
         * 为true时，余额不足时，系统不会改单，下单会失败，仅适用于币币市价单
         */
        private String banAmend;

        /**
         * 一键借币类型，仅适用于杠杆逐仓的一键借币模式：
         * manual：手动，auto_borrow： 自动借币，auto_repay： 自动还币
         * 默认是manual：手动
         */
        private String quickMgnType;

    }
}
