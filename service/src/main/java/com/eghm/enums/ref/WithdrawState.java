package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2024/2/27
 */
@Getter
@AllArgsConstructor
public enum WithdrawState {

    /**
     * 提现中
     */
    APPLY(0, "提现中"),

    /**
     * 提现成功
     */
    SUCCESS(1, "提现成功"),

    /**
     * 提现失败
     */
    FAIL(2, "提现失败");

    @JsonValue
    @EnumValue
    private final int value;

    @ExcelDesc
    private final String name;

}
