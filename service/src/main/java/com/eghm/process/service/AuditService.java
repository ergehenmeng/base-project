package com.eghm.process.service;

import com.eghm.dao.model.business.AuditRecord;
import com.eghm.model.dto.business.audit.AuditQueryRequest;
import com.eghm.process.dto.AuditProcess;
import com.eghm.process.dto.BeginProcess;
import com.github.pagehelper.PageInfo;

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
     * 分页获取用户所能看到的审核列表
     * @param request 前台请求参数
     * @return 审核列表
     */
    PageInfo<AuditRecord> getByPage(AuditQueryRequest request);
}
