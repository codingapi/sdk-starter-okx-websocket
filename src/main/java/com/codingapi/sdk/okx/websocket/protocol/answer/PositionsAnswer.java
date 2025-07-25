package com.codingapi.sdk.okx.websocket.protocol.answer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class PositionsAnswer {

    /**
     * 接受消息时间
     */
    private long localTs;

    /**
     * 产品类型
     */
    private String instType;
    /**
     * 保证金模式
     * cross：全仓
     * isolated：逐仓
     */
    private String mgnMode;
    /**
     * 持仓ID
     */
    private String posId;
    /**
     * 持仓方向
     * long：双向持仓多头
     * short：双向持仓空头
     * net：单向持仓（交割/永续/期权：pos为正代表多头，pos为负代表空头。币币杠杆：posCcy为交易货币时，代表多头；posCcy为计价货币时，代表空头。）
     */
    private String posSide;
    /**
     * 持仓数量，逐仓自主划转模式下，转入保证金后会产生pos为0的仓位
     */
    private String pos;
    /**
     * 交易币余额，适用于 币币杠杆（逐仓自主划转模式）
     */
    private String baseBal;
    /**
     * 计价币余额 ，适用于 币币杠杆（逐仓自主划转模式）
     */
    private String quoteBal;

    /**
     * 交易币已借，适用于 币币杠杆（逐仓一键借币模式）
     */
    private String baseBorrowed;
    /**
     * 交易币计息，适用于 币币杠杆（逐仓一键借币模式）
     */
    private String baseInterest;
    /**
     * 计价币已借，适用于 币币杠杆（逐仓一键借币模式）
     */
    private String quoteBorrowed;
    /**
     * 计价币计息，适用于 币币杠杆（逐仓一键借币模式）
     */
    private String quoteInterest;

    /**
     * 仓位资产币种，仅适用于币币杠杆仓位
     */
    private String posCcy;
    /**
     * 可平仓数量，适用于 币币杠杆,交割/永续（开平仓模式），期权（交易账户及保证金账户逐仓）。
     */
    private String availPos;
    /**
     * 开仓平均价
     */
    private String avgPx;
    /**
     * 未实现收益
     */
    private String upl;
    /**
     * 未实现收益率
     */
    private String uplRatio;
    /**
     * 产品ID，如 BTC-USD-180216
     */
    private String instId;
    /**
     * 杠杆倍数，不适用于期权
     */
    private String lever;
    /**
     * 预估强平价
     * 不适用于期权
     */
    private String liqPx;
    /**
     * 标记价格
     */
    private String markPx;
    /**
     * 初始保证金，仅适用于全仓
     */
    private String imr;
    /**
     * 保证金余额，可增减，仅适用于逐仓
     */
    private String margin;
    /**
     * 保证金率
     */
    private String mgnRatio;
    /**
     * 维持保证金
     */
    private String mmr;
    /**
     * 负债额，仅适用于币币杠杆
     */
    private String liab;
    /**
     * 负债币种，仅适用于币币杠杆
     */
    private String liabCcy;
    /**
     * 利息，已经生成的未扣利息
     */
    private String interest;
    /**
     * 最新成交ID
     */
    private String tradeId;
    /**
     * 期权市值，仅适用于期权
     */
    private String optVal;
    /**
     * 以美金价值为单位的持仓数量
     */
    private String notionalUsd;
    /**
     * 信号区
     * 分为5档，从1到5，数字越小代表adl强度越弱
     */
    private String adl;
    /**
     * 外部业务id，e.g. 体验券id
     */
    private String bizRefId;

    /**
     * 外部业务类型
     */
    private String bizRefType;
    /**
     * 占用保证金的币种
     */
    private String ccy;
    /**
     * 最新成交价
     */
    private String last;
    /**
     * 美金价格
     */
    private String usdPx;
    /**
     * 美金本位持仓仓位delta，仅适用于期权
     */
    private String deltaBS;
    /**
     * 币本位持仓仓位delta，仅适用于期权
     */
    private String deltaPA;
    /**
     * 美金本位持仓仓位gamma，仅适用于期权
     */
    private String gammaBS;
    /**
     * 币本位持仓仓位gamma，仅适用于期权
     */
    private String gammaPA;
    /**
     * 美金本位持仓仓位theta，仅适用于期权
     */
    private String thetaBS;
    /**
     * 币本位持仓仓位theta，仅适用于期权
     */
    private String thetaPA;
    /**
     * 美金本位持仓仓位vega，仅适用于期权
     */
    private String vegaBS;
    /**
     * 币本位持仓仓位vega，仅适用于期权
     */
    private String vegaPA;

    /**
     * 现货对冲占用数量
     * 适用于组合保证金模式
     */
    private String spotInUseAmt;
    /**
     * 现货对冲占用币种，如 BTC
     * 适用于组合保证金模式
     */
    private String spotInUseCcy;

    /**
     * 委托止盈止损
     */
    private List<CloseOrderAlgo> closeOrderAlgo;

    /**
     * 持仓创建时间，Unix时间戳的毫秒数格式，如 1597026383085
     */
    private long cTime;
    /**
     * 最近一次持仓更新时间，Unix时间戳的毫秒数格式，如 1597026383085
     */
    private long uTime;

    /**
     * 持仓信息的推送时间，Unix时间戳的毫秒数格式，如 1597026383085
     */
    private long pTime;


    @Setter
    @Getter
    @ToString
    public static class CloseOrderAlgo{
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
}
