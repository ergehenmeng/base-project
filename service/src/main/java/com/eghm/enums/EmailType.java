package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 映射 email_template表的nid字段
 *
 * @author 殿小二
 * @since 2020/8/28
 */
@AllArgsConstructor
@Getter
public enum EmailType {

    /**
     * 绑定邮箱时发送验证码
     */
    BIND_EMAIL("bind_email", "绑定邮箱", "bindEmailHandler"),

    /**
     * 更换邮箱 发送短信验证码
     */
    CHANGE_EMAIL("change_email", "更换邮箱", "changeEmailHandler"),

    /**
     * 找回密码时发送验证码
     */
    RETRIEVE_PASSWORD("retrieve_password", "找回密码", "retrievePasswordHandler"),

    /**
     * 定时任务报警
     */
    TASK_ALARM("task_alarm", "定时任务报警", "commonEmailHandler");

    private final String value;

    private final String msg;

    private final String handler;

}
