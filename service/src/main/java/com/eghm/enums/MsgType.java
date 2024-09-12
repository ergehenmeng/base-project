package com.eghm.enums;

import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2024/9/12
 */

@Getter
public enum MsgType {

    /**
     * 发货提醒消息
     */
    DELIVERY,

    /**
     * 退款审核消息
     */
    REFUND,
}
