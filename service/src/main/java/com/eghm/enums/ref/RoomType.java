package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 殿小二
 * @since 2024/3/31
 */
@AllArgsConstructor
@Getter
public enum RoomType {

    /**
     * 标间
     */
    STANDARD(1, "标间"),

    /**
     * 大床房
     */
    BIG_BED(2, "大床房"),

    /**
     * 双人房
     */
    DOUBLE(3, "双人房"),

    /**
     * 钟点房
     */
    HOUR(4, "钟点房"),

    /**
     * 套房
     */
    PACKAGE(5, "套房"),

    /**
     * 合租房
     */
    JOIN(6, "合租房");

    @JsonValue
    @EnumValue
    private final int value;

    @ExcelDesc
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RoomType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(RoomType.values()).filter(type -> value == type.value).findFirst().orElse(null);
    }
}
