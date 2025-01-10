package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2025/1/3
 */

@Getter
@AllArgsConstructor
public enum MemberState {

    /**
     * 正常
     */
    NORMAL(true, "正常"),

    /**
     * 注销
     */
    LOGOUT(false, "注销");

    /**
     * 状态
     */
    @EnumValue
    @JsonValue
    private final Boolean value;

    /**
     * 名称
     */
    @ExcelDesc
    private final String name;
}