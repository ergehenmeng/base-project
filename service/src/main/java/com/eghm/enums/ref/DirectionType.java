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
public enum DirectionType implements EnumBinder<Integer> {

    /**
     * 无须发货
     */
    INCOME(1, "收入"),

    /**
     * 快递包邮
     */
    DISBURSE(2, "支出"),

    ;

    @JsonValue
    @EnumValue
    private final Integer value;

    @ExcelDesc
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DirectionType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(DirectionType.values()).filter(type -> value.intValue() == type.value).findFirst().orElse(null);
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
