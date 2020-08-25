package com.eghm.process.service;

import com.eghm.dao.model.business.AuditRecord;

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
}
