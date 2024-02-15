package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonCreator;
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
public enum ChargeType implements EnumBinder {

    /**
     * 充值
     */
    RECHARGE(1, "充值"),

    /**
     * 支付收入(订单完成)
     */
    ORDER(2, "支付收入"),

    /**
     * 抽奖支出
     */
    DRAW(3, "抽奖支出"),

    /**
     * 提现支出
     */
    WITHDRAW(4, "提现支出"),

    /**
     * 关注赠送
     */
    ATTENTION_GIFT(5, "关注赠送"),
    ;

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

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ChargeType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(ChargeType.values()).filter(auditState -> auditState.value == value).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
