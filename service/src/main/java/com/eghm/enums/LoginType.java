package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2024/11/20
 */

@Getter
@AllArgsConstructor
public enum LoginType {

    /**
     * 账号密码
     */
    PASSWORD(1, "账号密码"),

    /**
     * 手机号+验证码
     */
    SMS(2, "手机号+验证码"),

    /**
     * 账号密码+验证码
     */
    PASSWORD_SMS(4, "账号密码+验证码"),

    /**
     * 扫码
     */
    QRCODE(8, "扫码"),

    /**
     * 账号密码+双因子
     */
    TOTP(16, "账号密码+双因子");

    private final int value;

    private final String desc;
}
