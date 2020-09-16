package com.eghm.common.enums;

/**
 * 映射 email_template表的nid字段
 * @author 殿小二
 * @date 2020/8/28
 */
public enum EmailType {

    /**
     * 绑定邮箱时发送验证码
     */
    BIND_EMAIL("bind_email","绑定邮箱", "bindEmailHandler"),

    /**
     * 更换邮箱 发送短信验证码
     */
    CHANGE_EMAIL("change_email","更换邮箱", "changeEmailHandler"),

    /**
     * 找回密码时发送验证码
     */
    RETRIEVE_PASSWORD("retrieve_password","找回密码", "retrievePasswordHandler"),

    /**
     * 定时任务报警
     */
    TASK_ALARM("task_alarm","定时任务报警", "commonEmailHandler"),

    ;

    private String value;


    private String msg;

    private String handler;

    EmailType(String value, String msg, String handler) {
        this.value = value;
        this.msg = msg;
        this.handler = handler;
    }

    public String getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

    public String getHandler() {
        return handler;
    }

}
