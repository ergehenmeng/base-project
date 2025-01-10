package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

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

}
