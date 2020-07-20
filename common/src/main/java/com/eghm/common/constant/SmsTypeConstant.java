package com.eghm.common.constant;

/**
 * 短信类型常量
 * @author 二哥很猛
 * @date 2019/8/20 9:58
 */
public class SmsTypeConstant {

    private SmsTypeConstant() {
    }

    /**
     * 未指定短信类型,则为默认短信:default
     */
    public static final String DEFAULT = "default";

    /**
     * 登陆短信
     */
    public static final String LOGIN_SMS = "login_sms";

    /**
     * 注册短信
     */
    public static final String REGISTER_SMS = "register_sms";
}
