package com.eghm.service.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付退款状态
 * @author 二哥很猛
 * @date 2022/7/24
 */
@Getter
@AllArgsConstructor
public enum RefundState {

    /**
     * 退款成功
     */
    SUCCESS,

    /**
     * 退款关闭
     */
    CLOSED,

    /**
     * 退款处理中
     */
    PROCESSING,

    /**
     * 退款异常
     */
    ABNORMAL,

    /**
     * 退款成功
     */
    REFUND_SUCCESS,
    ;
}
