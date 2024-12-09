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
 * @since 2024/1/11
 */

@Getter
@AllArgsConstructor
public enum CollectType implements EnumBinder<Integer> {

    /**
     * 资讯
     */
    NEWS(1, "资讯"),

    /**
     * 公告
     */
    NOTICE(2, "公告"),
    ;

    /**
     * 状态
     */
    @EnumValue
    @JsonValue
    private final Integer value;

    /**
     * 名称
     */
    @ExcelDesc
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CollectType of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(CollectType.values()).filter(couponMode -> couponMode.value == value.intValue()).findFirst().orElse(null);
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
