package com.eghm.pay.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 交易类型
 *
 * @author 二哥很猛
 */
@AllArgsConstructor
@Getter
public enum TradeType {

    /**
     * APP
     */
    WECHAT_APP("APP", "微信app支付", PayChannel.WECHAT),

    /**
     * JSAPI支付 与小程序是通用的
     */
    WECHAT_JSAPI("JSAPI", "JSAPI支付", PayChannel.WECHAT),

    /**
     * 微信小程序 与JSAPI是通用的
     */
    WECHAT_MINI("JSAPI", "微信小程序支付", PayChannel.WECHAT),

    /**
     * 微信扫码支付
     */
    WECHAT_NATIVE("NATIVE", "微信扫码支付", PayChannel.WECHAT),

    /**
     * H5支付 (微信外H5拉起支付)
     */
    WECHAT_H5("H5", "H5支付", PayChannel.WECHAT),

    /**
     * 支付宝app支付 (正常的app支付)
     */
    ALI_PAY("ALI_PAY", "支付宝支付", PayChannel.ALIPAY),

    /**
     * 支付宝PC扫码付
     */
    ALI_PC_PAY("ALI_PC_PAY", "支付宝PC扫码付", PayChannel.ALIPAY),

    /**
     * 零元付
     */
    ZERO_PAY("ZERO_PAY", "零元付", PayChannel.NONE)
    ;

    /**
     * 支付方式
     */
    private final String code;

    /**
     * 名字
     */
    private final String name;

    /**
     * 支付渠道
     */
    private final PayChannel payChannel;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TradeType of(String tradeType) {
        return Stream.of(TradeType.values()).filter(type -> type.getCode().equals(tradeType)).findFirst().orElse(null);
    }
}
