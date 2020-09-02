package com.eghm.common.enums;

/**
 * 映射 sms_template表中的nid字段
 * @author 殿小二
 * @date 2020/9/2
 */
public enum SmsType {
    /**
     * 未指定短信类型,则为默认短信:default
     */
    DEFAULT("default", "默认短信"),

    /**
     * 登陆发送短信
     */
    LOGIN_SMS("login_sms", "登陆发送短信"),

    /**
     * 注册发送短信
     */
    REGISTER_SMS("register_sms", "注册发送短信"),

    ;

    private String value;

    private String msg;

    SmsType(String value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public String getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
