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
 * @since 2024/1/9
 */
@Getter
@AllArgsConstructor
public enum ConfirmState implements EnumBinder {

    /**
     * 待确认
     */
    WAIT_CONFIRM(0, "待确认"),

    /**
     * 确认有房
     */
    SUCCESS_CONFIRM(1, "确认有房"),

    /**
     * 退款成功
     */
    FAIL_CONFIRM(2, "确认无房"),

    /**
     * 自动确认有房
     */
    AUTO_CONFIRM(3, "自动确认有房");

    /**
     * 状态
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
    public static ConfirmState of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(ConfirmState.values()).filter(auditState -> auditState.value == value).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
