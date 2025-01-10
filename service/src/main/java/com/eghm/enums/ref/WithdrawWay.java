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
public enum WithdrawWay {

    /**
     * 手动提现
     */
    MANUAL(1, "手动提现"),

    /**
     * 自动提现
     */
    AUTO(2, "自动提现");

    @JsonValue
    @EnumValue
    private final int value;

    @ExcelDesc
    private final String name;

}
