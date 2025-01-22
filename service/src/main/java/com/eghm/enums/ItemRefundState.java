package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 普通商品订单退款状态
 *
 * @author 二哥很猛
 * @since 2022/9/9
 */
@AllArgsConstructor
@Getter
public enum ItemRefundState {

    /**
     * 初始状态
     */
    INIT(0, "初始状态"),

    /**
     * 已退款
     */
    REFUND(1, "已退款");

    /**
     * 状态
     */
    @JsonValue
    @EnumValue
    private final int value;

    /**
     * 备注
     */
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ItemRefundState of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(ItemRefundState.values()).filter(type -> value == type.value).findFirst().orElse(null);
    }

}
