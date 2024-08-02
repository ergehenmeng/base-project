package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 核销方式
 *
 * @author 二哥很猛
 * @since 2024/7/5
 */
@Getter
@AllArgsConstructor
public enum VerificationType implements EnumBinder {

    /**
     * 手动核销 (扫码端手动核销)
     */
    MANUAL(1, "手动核销"),

    /**
     * 自动核销 (次日凌晨自动核销)
     */
    AUTO(2, "自动核销"),
    ;

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


    @Override
    public String toString() {
        return value + ":" + name;
    }
}
