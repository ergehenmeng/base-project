package com.eghm.enums;

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
public enum RoleType {

    /**
     * 系统角色
     */
    COMMON("common", "系统角色"),

    /**
     * 运营角色 (占坑)
     */
    OPERATE("operate", "运营角色");

    /**
     * 角色code
     */
    private final String value;

    /**
     * 角色名称
     */
    private final String name;
    public static RoleType of(String value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(RoleType.values()).filter(type -> value.equals(type.value)).findFirst().orElse(null);
    }
}
