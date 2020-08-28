package com.eghm.common.enums;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
public enum EmailCode {

    /**
     * 绑定邮箱时发送验证码
     */
    BIND_EMAIL("绑定邮箱", "bindEmailHandler"),

    /**
     * 找回密码时发送验证码
     */
    RETRIEVE_PASSWORD("找回密码", "retrievePasswordHandler"),

    ;
    private String msg;

    private String handler;

    EmailCode(String msg, String handler) {
        this.msg = msg;
        this.handler = handler;
    }

    public String getMsg() {
        return msg;
    }

    public String getHandler() {
        return handler;
    }
}
