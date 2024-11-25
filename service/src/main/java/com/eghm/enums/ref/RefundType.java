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
 * @since 2022/8/19
 */
@Getter
@AllArgsConstructor
public enum RefundType implements EnumBinder<Integer> {

    /**
     * 不支持退款
     */
    NOT_SUPPORTED(0, "不支持退款"),

    /**
     * 直接退款
     */
    DIRECT_REFUND(1, "直接退款"),

    /**
     * 审核后退款
     */
    AUDIT_REFUND(2, "审核后退款"),
    ;

    @EnumValue
    @JsonValue
    private final Integer value;

    @ExcelDesc
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RefundType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(RefundType.values()).filter(type -> value.intValue() == type.value).findFirst().orElse(null);
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
