package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Getter
@AllArgsConstructor
public enum TokenType {

    /**
     * JWT 支持同一用户多浏览器登录
     */
    JWT,

    /**
     * REDIS 只支持同一用户单浏览器登录
     */
    REDIS
}
