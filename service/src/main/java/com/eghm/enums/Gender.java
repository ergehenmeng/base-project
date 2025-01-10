package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2025/1/3
 */
@Getter
@AllArgsConstructor
public enum Gender {

    /**
     * 未知
     */
    NONE(0, "未知"),

    /**
     * 男
     */
    MALE(1, "男"),

    /**
     * 女
     */
    FEMALE(2, "女");

    @JsonValue
    @EnumValue
    private final int value;

    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Gender of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(Gender.values()).filter(auditState -> auditState.value == value)
                .findFirst().orElseThrow(() -> new BusinessException(ErrorCode.USER_TYPE_NULL));
    }
}