package com.eghm.enums;

import com.eghm.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2024/7/25
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
    private final int value;

    private final String name;
    public static Gender of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(Gender.values()).filter(auditState -> auditState.value == value)
                .findFirst().orElseThrow(() -> new BusinessException(ErrorCode.USER_TYPE_NULL));
    }

}
