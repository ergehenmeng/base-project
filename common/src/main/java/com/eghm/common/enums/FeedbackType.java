package com.eghm.common.enums;

import java.util.Arrays;

/**
 * @author 殿小二
 * @date 2020/9/14
 */
public enum FeedbackType {

    /**
     * 功能异常
     */
    EXCEPTION((byte) 1, "功能异常"),

    /**
     * 产品建议
     */
    ADVISE((byte)2, "产品建议"),

    /**
     * 安全问题
     */
    SECURITY((byte)3, "安全问题"),

    /**
     * 其他问题
     */
    OTHER((byte)4, "其他问题"),

    ;
    private byte value;

    private String msg;


    FeedbackType(byte value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public static FeedbackType getType(byte type) {
        return Arrays.stream(FeedbackType.values()).filter(feedbackType -> feedbackType.getValue() == type).findFirst().orElse(OTHER);
    }

    public byte getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
