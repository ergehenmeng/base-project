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
    SUCCESS("SUCCESS", "付款成功"),

    /**
     * 转入退款
     */
    REFUND("REFUND", "转入退款"),

    /**
     * 未支付
     */
    NOT_PAY("NOTPAY", "未支付"),

    /**
     * 已关闭
     */
    CLOSED("CLOSED", "已关闭"),

    /**
     * 已撤销(付款码支付)
     */
    REVOKED("REVOKED", "已撤销(付款码支付)"),

    /**
     * 用户支付中(付款码支付)
     */
    PAYING("USERPAYING", "用户支付中(付款码支付)"),

    /**
     * 支付失败
     */
    PAY_ERROR("PAYERROR", "支付失败"),

    /**
     * 已接收,等待扣款
     */
    ACCEPT("ACCEPT", "已接收,等待扣款")
    ;

    private String code;

    private String name;

    @JsonCreator
    public static TradeState forState(String code) {
        return Stream.of(TradeState.values()).filter(state -> state.getCode().equals(code)).findFirst().orElse(PAY_ERROR);
    }

}
