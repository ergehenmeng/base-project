package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.AuditRecord;
import com.eghm.model.dto.audit.AuditQueryRequest;

/**
 * @author 殿小二
 * @date 2020/8/25
 */
public interface AuditRecordService {

    /**
     * 不为空插入
     * @param auditRecord  record
     */
    void insert(AuditRecord auditRecord);

    /**
     * 不为空更新
     * @param auditRecord record
     */
    void updateById(AuditRecord auditRecord);

    /**
     * 主键查询
     * @param id id
     * @return audit
     */
    AuditRecord getById(Long id);

    /**
     * 根据角色和状态获取审批列表 分页
     * @param request 查询条件
     * @return 审核记录
     */
    Page<AuditRecord> getByPage(AuditQueryRequest request);
}
