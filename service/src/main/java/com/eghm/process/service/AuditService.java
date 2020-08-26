package com.eghm.process.service;

import com.eghm.common.enums.AuditState;
import com.eghm.dao.model.business.AuditRecord;
import com.eghm.process.dto.AuditProcess;
import com.eghm.process.dto.BeginProcess;

import java.util.List;

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
     * @param process 审批信息
     */
    void audit(AuditProcess process);

    /**
     * 获取用户所能看到的审核列表
     * @param operatorId 用户id
     * @param state 审核状态
     * @return 审核列表
     */
    List<AuditRecord> getAuditList(Integer operatorId, AuditState state);
}
