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
 * @since 2023/4/6
 */
@Getter
@AllArgsConstructor
public enum PrizeType implements EnumBinder {

    /**
     * 谢谢参与
     */
    NONE(0, "谢谢参与"),

    /**
     * 优惠券
     */
    COUPON(1, "优惠券"),

    /**
     * 积分
     */
    SCORE(2, "积分"),

    ;

    @EnumValue
    @JsonValue
    private final int value;

    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PrizeType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(PrizeType.values()).filter(prizeType -> prizeType.value == value).findFirst().orElse(null);
    }


    @Override
    public String toString() {
        return value + ":" + name;
    }
}
