package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2025/1/22
 */
@AllArgsConstructor
@Getter
public enum TabState {

    /**
     * 待付款
     */
    WAIT_AY(1, "待付款"),

    /**
     * 待收货
     */
    WAIT_DELIVERY(2, "待收货"),

    /**
     * 待评价
     */
    WAIT_EVALUATE(3, "待评价"),

    /**
     * 售后订单
     */
    AFTER_SALE(4, "售后订单");

    /**
     * 所属类型
     */
    @EnumValue
    @JsonValue
    private final int value;

    /**
     * 名称
     */
    @ExcelDesc
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TabState of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(TabState.values()).filter(type -> value == type.value).findFirst().orElse(null);
    }

}
