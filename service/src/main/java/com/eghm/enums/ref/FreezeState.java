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
 * @since 2024/1/12
 */

@Getter
@AllArgsConstructor
public enum FreezeState implements EnumBinder {

    /**
     * 冻结中
     */
    FREEZE(1, "冻结中"),

    /**
     * 已解冻
     */
    UNFREEZE(2, "已解冻");

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
    public static FreezeState of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(FreezeState.values()).filter(auditState -> auditState.value == value).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
