package com.eghm.process.service;

import com.eghm.common.enums.AuditState;
import com.eghm.dao.model.business.AuditRecord;

import java.util.List;

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
     * 根据角色和状态获取审批列表
     * @param roleList 角色列表
     * @param state 状态
     * @return 审核记录
     */
    List<AuditRecord> getAuditList(List<String> roleList, AuditState state);
}
