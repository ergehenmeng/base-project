package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@Getter
@AllArgsConstructor
public enum AccountType {

    /**
     * 例如: 平台抽佣:5%, 单价500x2, 则支付收入500x2*0.95=950
     * 订单支付
     */
    ORDER_PAY(1, "订单收入", 1),

    /**
     * 例如: 平台抽佣:5%, 单价500x2, 则支付收入500x2*0.95=950
     * 订单退款
     */
    ORDER_REFUND(2, "订单退款", 2),

    /**
     * 积分提现收入
     */
    SCORE_WITHDRAW(3, "积分提现收入", 1),

    /**
     * 例如提现提现1000, 手续费5块,则提现冻结1000, 到账995
     */
    WITHDRAW(4, "提现支出", 2),

    /**
     * 积分充值支出
     */
    SCORE_RECHARGE(5, "积分充值支出", 2);

    /**
     * 状态值
     */
    @EnumValue
    @JsonValue
    private final int value;

    /**
     * 名称
     */
    @ExcelDesc
    private final String name;

    /**
     * 支出或收入 (1:收入 2:支出)
     */
    private final int direction;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static AccountType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(AccountType.values()).filter(auditState -> auditState.value == value).findFirst().orElse(null);
    }

}
