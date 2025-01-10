package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@Getter
@AllArgsConstructor
public enum EnterpriseType {

    /**
     * 个体工商户
     */
    INDIVIDUAL(1, "个体工商户"),

    /**
     * 企业
     */
    ENTERPRISE(2, "企业");

    /**
     * 状态
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
    public static EnterpriseType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(EnterpriseType.values()).filter(type -> value == type.value).findFirst().orElse(null);
    }

}
