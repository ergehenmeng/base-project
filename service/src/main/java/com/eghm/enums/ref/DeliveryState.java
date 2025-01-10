package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */
@Getter
@AllArgsConstructor
public enum DeliveryState {

    /**
     * 初始状态
     */
    INIT(0, "初始状态"),

    /**
     * 待发货
     */
    WAIT_DELIVERY(1, "待发货"),

    /**
     * 待收货
     */
    WAIT_TAKE(2, "待收货"),

    /**
     * 待自提
     */
    PICK_UP(3, "待自提"),

    /**
     * 已签收/已收货
     */
    CONFIRM_TASK(4, "已签收");

    @JsonValue
    @EnumValue
    private final int value;

    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DeliveryState of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(DeliveryState.values()).filter(type -> value == type.value).findFirst().orElse(null);
    }

}
