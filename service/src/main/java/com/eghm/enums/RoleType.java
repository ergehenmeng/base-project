package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛 2022/6/24 17:36
 */
@Getter
@AllArgsConstructor
public enum RoleType implements ValueEnumBinder {

    /**
     * 系统通用角色
     */
    COMMON("common", "系统通用角色"),

    /**
     * 核销员 (待完成)
     */
    VERIFY("verify", "核销员"),
    ;

    /**
     * 角色code
     */
    @EnumValue
    @JsonValue
    private final String value;

    /**
     * 角色名称
     */
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public static RoleType of(@JsonProperty("value") String value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(RoleType.values()).filter(type -> value.equals(type.value)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
