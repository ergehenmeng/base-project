package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 角色类型
 *
 * @author 二哥很猛 2022/6/24 17:36
 */
@Getter
@AllArgsConstructor
public enum RoleType implements EnumBinder<String> {

    /**
     * 系统角色
     */
    COMMON("common", "系统角色"),

    /**
     * 运营角色 (占坑)
     */
    OPERATE("operate", "运营角色"),
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

    @Override
    public boolean match(String value) {
        return this.value.equals(value.split(":")[0]);
    }
}
