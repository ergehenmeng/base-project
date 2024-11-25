package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonCreator;
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
public enum LotteryState implements EnumBinder<Integer> {

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
    END(2, "已结束"),

    ;
    /**
     * 状态值
     */
    @JsonValue
    @EnumValue
    private final Integer value;

    /**
     * 名称
     */
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static LotteryState of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(LotteryState.values()).filter(type -> value.intValue() == type.value).findFirst().orElse(null);
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
