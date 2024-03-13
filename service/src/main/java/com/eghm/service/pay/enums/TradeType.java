package com.eghm.service.pay.enums;


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
    WECHAT_APP("APP", "微信app支付"),

    /**
     * JSAPI支付 与小程序是通用的
     */
    WECHAT_JSAPI("JSAPI", "JSAPI支付"),

    /**
     * 微信小程序 与JSAPI是通用的
     */
    WECHAT_MINI("JSAPI", "微信小程序支付"),

    /**
     * 微信扫描支付
     */
    WECHAT_NATIVE("NATIVE", "微信扫描支付"),

    /**
     * H5支付 (微信外H5拉起支付)
     */
    WECHAT_H5("H5", "H5支付"),

    /**
     * 支付宝app支付 (正常的app支付)
     */
    ALI_PAY("ALI_PAY", "支付宝支付"),

    /**
     * 支付宝当面付
     */
    ALI_FACE_PAY("ALI_FACE_PAY", "支付宝当面付"),

    ;

    /**
     * 支付方式
     */
    private final String code;

    /**
     * 名字
     */
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TradeType of(String tradeType) {
        return Stream.of(TradeType.values()).filter(type -> type.getCode().equals(tradeType)).findFirst().orElse(null);
    }
}
