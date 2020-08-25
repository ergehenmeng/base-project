package com.eghm.process.service;

import com.eghm.process.dto.BeginProcess;

/**
 * 审核相关的所有逻辑
 * @author 殿小二
 * @date 2020/8/25
 */
public interface AuditService {

    /**
     * 开始发起审核流程
     * @param process 申请信息
     */
    void beginProcess(BeginProcess process);

    /**
     * 审核
     */
    void audit();
}
