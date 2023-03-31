package com.eghm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 殿小二
 * @date 2020/9/14
 */
@AllArgsConstructor
@Getter
public enum FeedbackType {

    /**
     * 功能异常
     */
    EXCEPTION(1, "功能异常"),

    /**
     * 产品建议
     */
    ADVISE(2, "产品建议"),

    /**
     * 安全问题
     */
    SECURITY(3, "安全问题"),

    /**
     * 其他问题
     */
    OTHER(4, "其他问题"),

    ;
    private final int value;

    private final String msg;

    public static FeedbackType getType(Integer type) {
        if (type == null) {
            return null;
        }
        return Arrays.stream(FeedbackType.values()).filter(feedbackType -> feedbackType.getValue() == type).findFirst().orElse(OTHER);
    }

}
