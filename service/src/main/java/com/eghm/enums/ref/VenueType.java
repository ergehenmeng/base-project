package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Getter
@AllArgsConstructor
public enum VenueType implements EnumBinder {

    /**
     * 篮球馆
     */
    BASKETBALL(1, "篮球馆"),

    /**
     * 网球馆
     */
    TENNIS(2, "网球馆"),

    /**
     * 羽毛馆
     */
    BADMINTON(4, "羽毛球馆"),

    /**
     * 乒乓球馆
     */
    PING_PONG(5, "乒乓球馆"),

    /**
     * 游泳馆
     */
    SWIMMING(6, "游泳馆"),

    /**
     * 健身馆
     */
    GYM(7, "健身馆"),

    /**
     * 瑜伽馆
     */
    YOGA(8, "瑜伽馆"),

    /**
     * 羽毛馆
     */
    BOWLING(9, "保龄馆"),

    /**
     * 足球馆
     */
    FOOTBALL(10, "足球馆"),

    /**
     * 排球馆
     */
    VOLLEYBALL(11, "排球馆"),

    /**
     * 田径馆
     */
    ATHLETICS(12, "田径馆"),

    /**
     * 综合馆
     */
    GYMNASIUM(13, "综合馆"),

    /**
     * 跆拳道馆
     */
    TAEKWONDO(14, "跆拳道馆");


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
    public static VenueType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(VenueType.values()).filter(auditState -> auditState.value == value).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
