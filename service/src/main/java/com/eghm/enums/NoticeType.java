package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 殿小二
 * @since 2020/9/12
 */
@AllArgsConstructor
@Getter
public enum NoticeType {

    /**
     * 通用类通知
     */
    COMMON("common", "通用类通知", false, ""),

    /**
     * 营销通知
     */
    MARKETING("marketing", "营销通知", false, ""),

    /**
     * 用户反馈结果处理的通知
     */
    FEEDBACK_PROCESS("feedback_process", "反馈处理", true, ""),

    /**
     * 用户反馈结果处理的通知
     */
    EVALUATION_REFUSE("evaluation_refuse", "订单评价内容不合规", false, "");

    /**
     * 分类
     */
    @EnumValue
    @JsonValue
    private final String value;

    /**
     * 说明
     */
    private final String msg;

    /**
     * 是否发送消息通知(极光,友盟等通知)
     */
    private final boolean pushNotice;

    /**
     * 通知跳转页面
     */
    private final String viewTag;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static NoticeType of(String value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(NoticeType.values()).filter(auditState -> value.equals(auditState.value)).findFirst().orElse(null);
    }
}
