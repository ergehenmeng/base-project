package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.JsonDesc;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 殿小二
 * @since 2020/9/14
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
    OTHER(4, "其他问题");

    @JsonValue
    @EnumValue
    private final int value;

    @JsonDesc
    private final String msg;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static FeedbackType of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(FeedbackType.values()).filter(feedbackType -> feedbackType.getValue() == value).findFirst().orElse(OTHER);
    }

}
