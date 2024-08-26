package com.eghm.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2022/7/24
 */
@Getter
@AllArgsConstructor
public enum RefundChannel {

    /**
     * 原路退款
     */
    ORIGINAL,

    /**
     * 退回到余额
     */
    BALANCE,

    /**
     * 原账户异常退到其他余额账户
     */
    OTHER_BALANCE,

    /**
     * 原银行卡异常退到其他银行卡
     */
    OTHER_BANKCARD

}
