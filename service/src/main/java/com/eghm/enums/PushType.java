package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @date 2019/8/29 16:12
 */
@AllArgsConstructor
@Getter
public enum PushType {

    /**
     * 站内性通知
     */
    INNER_EMAIL("inner_email");

    private final String nid;
}
