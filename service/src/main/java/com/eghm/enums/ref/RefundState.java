package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonCreator;
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
public enum RefundState implements EnumBinder<Integer> {

    /**
     * 初始状态, 未退款
     */
    NONE(0, "未退款"),

    /**
     * 退款申请中,待审核
     */
    APPLY(1, "退款申请中"),

    /**
     * 退款中
     */
    PROGRESS(2, "退款中"),

    /**
     * 退款拒绝
     */
    REFUSE(3, "退款拒绝"),

    /**
     * 退款成功
     */
    SUCCESS(4, "退款成功"),

    /**
     * 退款失败
     */
    FAIL(5, "退款失败"),

    /**
     * 线下退款成功
     */
    OFFLINE(6, "线下退款成功");

    /**
     * 状态
     */
    @EnumValue
    @JsonValue
    private final Integer value;

    /**
     * 名称
     */
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RefundState of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(RefundState.values()).filter(type -> value.intValue() == type.value).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }

    @Override
    public boolean match(String value) {
        return this.value == Integer.parseInt(value.split(":")[0]);
    }
}
