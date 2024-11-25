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
 * 订单关闭类型
 *
 * @author 二哥很猛
 * @since 2022/7/28
 */
@Getter
@AllArgsConstructor
public enum CloseType implements EnumBinder<Integer> {

    /**
     * 过期自动取消
     */
    EXPIRE(1, "过期自动取消"),

    /**
     * 用户取消
     */
    CANCEL(2, "用户取消"),

    /**
     * 退款成功
     */
    REFUND(3, "退款成功"),

    ;
    /**
     * 状态
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
    public static CloseType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(CloseType.values()).filter(auditState -> auditState.value == value.intValue()).findFirst().orElse(null);
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
