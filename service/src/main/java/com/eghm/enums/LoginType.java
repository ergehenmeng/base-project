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
     * 密码
     */
    PASSWORD(1, "密码"),

    /**
     * 短信
     */
    SMS(2, "短信"),

    /**
     * 密码+短信
     */
    PASSWORD_SMS(4, "密码+短信"),

    /**
     * 扫码
     */
    QRCODE(8, "扫码"),
    ;

    private final int value;

    private final String desc;
}
