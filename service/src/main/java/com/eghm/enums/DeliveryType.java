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
 * @author 殿小二
 * @since 2022/9/4
 */
@AllArgsConstructor
@Getter
public enum DeliveryType {

    /**
     * 默认
     */
    NONE(0, "无需发货"),

    /**
     * 包邮
     */
    EXPRESS(1, "包邮"),

    /**
     * 自提
     */
    SELF_PICK(2, "自提"),

    /**
     * 快递+自提
     */
    EXPRESS_PICK(3, "快递/自提");

    @JsonValue
    @EnumValue
    private final int value;

    @ExcelDesc
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DeliveryType of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(DeliveryType.values()).filter(type -> value == type.value).findFirst().orElse(null);
    }

}
