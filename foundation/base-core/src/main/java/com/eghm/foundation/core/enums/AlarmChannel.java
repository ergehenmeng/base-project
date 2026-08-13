package com.eghm.foundation.core.enums;

import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2024/3/25
 */

@Getter
public enum AlarmChannel {

    /**
     * 默认
     */
    DEFAULT,

    /**
     * 钉钉报警通知
     */
    DING_TALK,

    /**
     * 飞书报警通知
     */
    FEI_SHU,
    
    /**
     * 企业微信报警通知
     */
    ENTERPRISE_WECHAT
}
