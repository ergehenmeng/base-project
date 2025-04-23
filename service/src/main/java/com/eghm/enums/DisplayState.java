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
public enum DisplayState {

    /**
     * 商户菜单
     */
    MERCHANT(1, "商户菜单"),

    /**
     * 系统菜单
     */
    SYSTEM(2, "系统菜单"),

    /**
     * 通用菜单
     */
    ALL(3, "通用菜单");

    /**
     * 菜单所属类型
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
    public static DisplayState of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(DisplayState.values()).filter(type -> value == type.value).findFirst().orElse(null);
    }

}
