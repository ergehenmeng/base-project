package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@Getter
@AllArgsConstructor
public enum ChangeType {

    /**
     * 支付冻结
     */
    PAY_FREEZE(1, "支付冻结"),

    /**
     * 退款解冻
     */
    REFUND_UNFREEZE(2, "退款解冻"),

    /**
     * 订单完成解冻
     */
    COMPLETE_UNFREEZE(3, "订单完成解冻");

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
    public static ChangeType of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(ChangeType.values()).filter(auditState -> auditState.value == value).findFirst().orElse(null);
    }

}
