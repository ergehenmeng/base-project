package com.eghm.service.pay.enums;


import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 交易类型
 * @author 二哥很猛
 */
@AllArgsConstructor
@Getter
public enum TradeType {

    /**
     * 公众号支付
     */
    JS_API("JSAPI", "公众号支付"),

    /**
     * 扫码支付
     */
    NATIVE("NATIVE", "扫码支付"),

    /**
     * APP支付
     */
    APP("APP", "APP支付"),

    /**
     * 付款码支付
     */
    QR_PAY("MICROPAY", "付款码支付"),

    /**
     * H5支付
     */
    H5("MWEB", "H5支付"),

    /**
     * 刷脸支付
     */
    FACE_PAY("FACEPAY", "刷脸支付"),

    ;

    /**
     * 支付方式
     */
    private String code;

    /**
     * 名字
     */
    private String name;

    @JsonCreator
    public static TradeType forType(String tradeType) {
        return Stream.of(TradeType.values()).filter(type -> type.getCode().equals(tradeType)).findFirst().orElseThrow(() -> new BusinessException(ErrorCode.UNKNOWN_PAY_TYPE));
    }
}
