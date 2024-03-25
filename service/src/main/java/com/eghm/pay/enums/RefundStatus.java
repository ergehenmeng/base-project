package com.eghm.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付退款状态(第三方支付的退款状态)
 *
 * @author 二哥很猛
 * @since 2022/7/24
 */
@Getter
@AllArgsConstructor
public enum RefundStatus {

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
