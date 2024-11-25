package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonCreator;
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
public enum DeliveryType implements EnumBinder<Integer> {

    /**
     * 快递包邮
     */
    EXPRESS(1, "快递包邮"),

    /**
     * 门店自提
     */
    SELF_PICK(2, "门店自提"),
    ;

    @JsonValue
    @EnumValue
    private final Integer value;

    @ExcelDesc
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DeliveryType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(DeliveryType.values()).filter(type -> value.intValue() == type.value).findFirst().orElse(null);
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
