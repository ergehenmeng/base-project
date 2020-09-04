package com.eghm.service.common;

import com.eghm.dao.model.AuditRecord;
import com.eghm.model.dto.audit.AuditQueryRequest;
import com.github.pagehelper.PageInfo;

/**
 * @author 殿小二
 * @date 2020/8/25
 */
public interface AuditRecordService {

    /**
     * 不为空插入
     * @param record  record
     */
    void insertSelective(AuditRecord record);

    /**
     * 不为空更新
     * @param record record
     */
    void updateSelective(AuditRecord record);

    /**
     * 主键查询
     * @param id id
     * @return audit
     */
    AuditRecord getById(Integer id);

    /**
     * 根据角色和状态获取审批列表 分页
     * @param request 查询条件
     * @return 审核记录
     */
    PageInfo<AuditRecord> getByPage(AuditQueryRequest request);
}
