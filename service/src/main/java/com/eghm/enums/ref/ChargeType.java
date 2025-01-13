package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2024/2/14
 */

@Getter
@AllArgsConstructor
public enum ChargeType {

    /**
     * 充值
     */
    RECHARGE(1, "充值", 1),

    /**
     * 支付收入(订单支付)
     */
    ORDER_PAY(2, "支付收入", 1),

    /**
     * 支付退款(订单退款)
     */
    ORDER_REFUND(3, "支付退款", 2),

    /**
     * 抽奖支出
     */
    DRAW(4, "抽奖支出", 2),

    /**
     * 提现支出
     */
    WITHDRAW(5, "提现支出", 2),

    /**
     * 关注赠送
     */
    ATTENTION_GIFT(6, "关注赠送", 2),

    /**
     * 提现失败解冻
     */
    WITHDRAW_FAIL(7, "提现失败", 1);

    /**
     * 状态值
     */
    @EnumValue
    @JsonValue
    private final int value;

    /**
     * 名称
     */
    private final String name;

    /**
     * 1:收入 2:支出
     */
    private final int direction;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ChargeType of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(ChargeType.values()).filter(auditState -> auditState.value == value).findFirst().orElse(null);
    }
}
