package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2023/12/9
 */

@Getter
@AllArgsConstructor
public enum Duration {

    ONE(1, "一日游"),

    TWO(2, "两日游"),

    THREE(3, "三日游"),

    FOUR(4, "四日游"),

    FIVE(5, "五日游"),

    SIX(6, "六日游"),

    SEVEN(7, "七日游"),

    EIGHT(8, "八日游"),

    NINE(9, "九日游"),

    TEN(10, "十日游"),

    ELEVEN(11, "十一日游"),

    TWELVE(12, "十二日游"),

    THIRTEEN(13, "十三日游"),

    FOURTEEN(14, "十四日游"),

    FIFTEEN(15, "十五日游");

    @JsonValue
    @EnumValue
    private final int value;

    @ExcelDesc
    private final String name;

}
