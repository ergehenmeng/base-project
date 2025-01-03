package com.eghm.enums;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@Getter
@AllArgsConstructor
public enum MemberState implements EnumBinder<Boolean> {

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

    @Override
    public String toString() {
        return value + ":" + name;
    }

    @Override
    public boolean match(String value) {
        return this.value == BooleanUtil.toBoolean(value);
    }
}
