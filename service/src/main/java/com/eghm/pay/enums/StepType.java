package com.eghm.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2022/7/27
 */
@Getter
@AllArgsConstructor
public enum StepType {

    /**
     * 支付
     */
    PAY,

    /**
     * 退款
     */
    REFUND

}
