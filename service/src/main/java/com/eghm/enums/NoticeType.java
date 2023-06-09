package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 殿小二
 * @date 2020/9/12
 */
@AllArgsConstructor
@Getter
public enum NoticeType {

    /**
     * 通用类通知
     */
    COMMON("COMMON", "通用类通知", false, "memberNotice"),

    /**
     * 用户反馈结果处理的通知
     */
    FEEDBACK_PROCESS("FEEDBACK_PROCESS", "反馈处理", true, "memberNotice");

    /**
     * 分类
     */
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
}
