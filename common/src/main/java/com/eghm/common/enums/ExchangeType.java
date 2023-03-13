package com.eghm.common.enums;

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
     * Fanout exchange.
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
