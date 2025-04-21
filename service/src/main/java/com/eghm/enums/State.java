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
 * @since 2022/7/2
 */
@Getter
@AllArgsConstructor
public enum State {

    /**
     * 待上架
     */
    UN_SHELVE(0, "待上架"),

    /**
     * 已上架
     */
    SHELVE(1, "已上架"),

    /**
     * 平台下架
     */
    FORCE_UN_SHELVE(2, "平台下架");

    /**
     * 状态值
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
    public static State of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(State.values()).filter(type -> type.value == value).findFirst().orElse(null);
    }
}
