package com.eghm.service.pay.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_5_2.shtml
 * @author 二哥很猛
 */
@AllArgsConstructor
@Getter
public enum TradeState {

    /**
     * 付款成功
     */
    SUCCESS("SUCCESS", "付款成功", PayChannel.WECHAT),

    /**
     * 转入退款
     */
    REFUND("REFUND", "转入退款", PayChannel.WECHAT),

    /**
     * 未支付
     */
    NOT_PAY("NOTPAY", "未支付", PayChannel.WECHAT),

    /**
     * 已关闭
     */
    CLOSED("CLOSED", "已关闭", PayChannel.WECHAT),

    /**
     * 已撤销(付款码支付)
     */
    REVOKED("REVOKED", "已撤销(付款码支付)", PayChannel.WECHAT),

    /**
     * 用户支付中(付款码支付)
     */
    PAYING("USERPAYING", "用户支付中(付款码支付)", PayChannel.WECHAT),

    /**
     * 支付失败
     */
    PAY_ERROR("PAYERROR", "支付失败", PayChannel.WECHAT),

    /**
     * 已接收,等待扣款
     */
    ACCEPT("ACCEPT", "已接收,等待扣款", PayChannel.WECHAT),

    /**
     * 交易创建，等待买家付款
     */
    WAIT_BUYER_PAY("WAIT_BUYER_PAY", "交易创建,等待买家付款", PayChannel.ALIPAY),

    /**
     * 未付款交易超时关闭,或支付完成后全额退款
     */
    TRADE_CLOSED("TRADE_CLOSED", "未付款交易超时关闭,或支付完成后全额退款", PayChannel.ALIPAY),

    /**
     * 交易支付成功
     */
    TRADE_SUCCESS("TRADE_SUCCESS", "交易支付成功", PayChannel.ALIPAY),

    /**
     * 交易结束，不可退款
     */
    TRADE_FINISHED("TRADE_FINISHED", "交易支付成功", PayChannel.ALIPAY),
    ;

    private final String code;

    private final String name;

    private final PayChannel channel;

    @JsonCreator
    public static TradeState forState(String code) {
        return Stream.of(TradeState.values()).filter(state -> state.getCode().equals(code)).findFirst().orElse(PAY_ERROR);
    }

}
