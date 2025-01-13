package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2024/8/12
 */

@Getter
@AllArgsConstructor
public enum SelectType {

    /**
     * 按周查询
     */
    WEEK("week", "周"),

    /**
     * 按月查询
     */
    MONTH("month", "月"),

    /**
     * 按年查询
     */
    YEAR("year", "年"),

    /**
     * 自定义
     */
    CUSTOM("custom", "自定义");

    @JsonValue
    @EnumValue
    private final String value;

    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SelectType of(@JsonProperty("value") String value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(SelectType.values()).filter(select -> select.value.equals(value)).findFirst().orElse(null);
    }

}
