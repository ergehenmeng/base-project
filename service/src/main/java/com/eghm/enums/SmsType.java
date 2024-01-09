package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 映射 sms_template表中的nid字段
 * @author 殿小二
 * @date 2020/9/2
 */
@AllArgsConstructor
@Getter
public enum SmsType {
    /**
     * 未指定短信类型,则为默认短信:default
     */
    DEFAULT("default", "默认类型短信"),

    /**
     * 登陆发送短信
     */
    LOGIN("login", "登陆发送短信"),

    /**
     * 忘记登陆密码
     */
    FORGET("login", "忘记密码发送短信"),

    /**
     * 注册发送短信
     */
    REGISTER("register", "注册发送短信"),

    /**
     * 更换邮箱发送短信验证码
     */
    CHANGE_EMAIL("change_email", "更换邮箱发送短信验证码"),

    /**
     * 确认无房,发送通知短信
     */
    CONFIRM_NO_ROOM("confirm_no_room", "确认无房,发送通知短信"),

    ;
    private final String value;

    private final String msg;
}
