package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2024/3/26
 */

@Getter
@AllArgsConstructor
public enum WeChatVersion {

    /**
     * 正式版
     */
    RELEASE("release"),

    /**
     * 体验版
     */
    TRIAL("trial"),

    /**
     * 开发版
     */
    DEVELOP("develop");

    /**
     * 小程序版本
     */
    private final String value;
}
