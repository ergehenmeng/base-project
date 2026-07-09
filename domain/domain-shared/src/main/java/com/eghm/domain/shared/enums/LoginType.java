package com.eghm.domain.shared.enums;

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
     * 扫码
     */
    QRCODE(4, "扫码");

    private final int value;

    private final String desc;
}
