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
     * JWT
     */
    JWT,

    /**
     * REDIS
     */
    REDIS
}
