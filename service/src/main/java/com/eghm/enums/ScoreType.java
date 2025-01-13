package com.eghm.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 殿小二
 * @since 2020/9/7
 */
@AllArgsConstructor
@Getter
public enum ScoreType {

    /**
     * 签到积分
     */
    SIGN_IN(1, "签到积分", 1),

    /**
     * 抽奖积分
     */
    LOTTERY(2, "抽奖积分", 1),

    /**
     * 支付积分
     */
    PAY(3, "支付积分", 2),

    /**
     * 支付取消
     */
    PAY_CANCEL(4, "支付取消", 1),

    /**
     * 退款
     */
    REFUND(5, "退款", 1),

    /**
     * 系统扣除
     */
    DEDUCTION(6, "系统扣除", 2),

    /**
     * 系统奖励
     */
    AWARD(7, "系统奖励", 1);

    /**
     * 类型
     */
    private final int value;

    /**
     * 说明
     */
    private final String msg;

    /**
     * 1: 收入 2: 支出
     */
    private final Integer direction;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ScoreType of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(ScoreType.values()).filter(scoreType -> scoreType.getValue() == value).findFirst().orElse(null);
    }

}
