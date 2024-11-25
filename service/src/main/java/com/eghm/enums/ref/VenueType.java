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
public enum VenueType implements EnumBinder<Integer> {

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
    BADMINTON(3, "羽毛球馆"),

    /**
     * 乒乓球馆
     */
    PING_PONG(4, "乒乓球馆"),

    /**
     * 游泳馆
     */
    SWIMMING(5, "游泳馆"),

    /**
     * 健身馆
     */
    GYM(6, "健身馆"),

    /**
     * 瑜伽馆
     */
    YOGA(7, "瑜伽馆"),

    /**
     * 羽毛馆
     */
    BOWLING(8, "保龄馆"),

    /**
     * 足球馆
     */
    FOOTBALL(9, "足球馆"),

    /**
     * 排球馆
     */
    VOLLEYBALL(10, "排球馆"),

    /**
     * 田径馆
     */
    ATHLETICS(11, "田径馆"),

    /**
     * 综合馆
     */
    GYMNASIUM(12, "综合馆"),

    /**
     * 跆拳道馆
     */
    TAEKWONDO(13, "跆拳道馆");


    /**
     * 状态值
     */
    @EnumValue
    @JsonValue
    private final Integer value;

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
        return Arrays.stream(VenueType.values()).filter(auditState -> auditState.value == value.intValue()).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }

    @Override
    public boolean match(String value) {
        return this.value == Integer.parseInt(value.split(":")[0]);
    }
}
