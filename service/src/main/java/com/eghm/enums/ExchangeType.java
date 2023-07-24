package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2023/3/13
 */
@Getter
@AllArgsConstructor
public enum ExchangeType {

    /**
     * Direct exchange.
     */
    DIRECT,

    /**
     * Topic exchange
     */
    TOPIC,

    /**
     * 广播方式
     */
    FANOUT,

    /**
     * Headers exchange.
     */
    HEADERS,

    /**
     * System exchange.
     */
    SYSTEM,
}
