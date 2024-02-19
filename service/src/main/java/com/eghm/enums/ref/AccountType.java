package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonCreator;
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
public enum AccountType implements EnumBinder {

    /**
     * 例如: 平台抽佣:5%, 单价500x2, 则支付收入500x2*0.95=950
     * 支付收入 (不含平台抽佣)
     */
    ORDER(1, "订单收入"),

    /**
     * 例如提现提现1000, 手续费5块,则提现冻结1000, 到账995
     */
    WITHDRAW(2, "提现支出"),

    ;

    /**
     * 状态值
     */
    @EnumValue
    @JsonValue
    private final int value;

    /**
     * 名称
     */
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static AccountType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(AccountType.values()).filter(auditState -> auditState.value == value).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
