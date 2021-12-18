package com.eghm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 殿小二
 * @date 2020/8/25
 */
@AllArgsConstructor
@Getter
public enum AuditState {

    /**
     * 待审核
     */
    APPLY((byte) 0, "待审核"),

    /**
     * 审核通过
     */
    PASS((byte) 1, "审核通过") ,

    /**
     * 审核拒绝
     */
    REFUSE((byte) 2, "审核拒绝"),
    ;

    private final byte value;

    private final String msg;

}
