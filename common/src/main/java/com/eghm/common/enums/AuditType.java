package com.eghm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审核类型
 * @author 殿小二
 * @date 2020/8/25
 */
@AllArgsConstructor
@Getter
public enum AuditType {

    /**
     * 开户申请
     */
    OPEN_ACCOUNT("开户申请", "OA.", "defaultAuditHandler");

    /**
     * 显示信息
     */
    private final String msg;

    /**
     * 审批单号前缀
     */
    private final String prefix;

    /**
     * 审批处理逻辑
     */
    private final String handler;

}
