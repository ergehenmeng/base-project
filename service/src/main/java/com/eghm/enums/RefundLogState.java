package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 订单退款状态(本地订单的退款状态)
 *
 * @author 二哥很猛
 * @since 2022/7/28
 */
@Getter
@AllArgsConstructor
public enum RefundLogState {

    /**
     * 退款中
     */
    REFUNDING(0, "退款中"),

    /**
     * 退款成功
     */
    SUCCESS(1, "退款成功"),

    /**
     * 退款失败
     */
    FAIL(2, "退款失败"),

    /**
     * 取消退款
     */
    CANCEL(3, "取消退款"),

    /**
     * 拒绝退款
     */
    REFUSE(4, "拒绝退款");

    /**
     * 状态
     */
    @EnumValue
    @JsonValue
    private final int value;

    /**
     * 名称
     */
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RefundLogState of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(RefundLogState.values()).filter(type -> value == type.value).findFirst().orElse(null);
    }
}
