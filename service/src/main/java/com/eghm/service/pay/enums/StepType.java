package com.eghm.service.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @date 2022/7/27
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
    REFUND;

}
