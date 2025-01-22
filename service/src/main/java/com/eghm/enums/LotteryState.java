package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author wyb
 * @since 2023/3/28 14:50
 */
@Getter
@AllArgsConstructor
public enum LotteryState {

    /**
     * 未开始
     */
    INIT(0, "未开始"),

    /**
     * 进行中
     */
    START(1, "进行中"),

    /**
     * 已结束
     */
    END(2, "已结束");

    /**
     * 状态值
     */
    @JsonValue
    @EnumValue
    private final int value;

    /**
     * 名称
     */
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static LotteryState of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(LotteryState.values()).filter(type -> value == type.value).findFirst().orElse(null);
    }
}
