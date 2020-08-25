package com.eghm.process.service.impl;

import com.eghm.dao.mapper.business.AuditRecordMapper;
import com.eghm.dao.model.business.AuditRecord;
import com.eghm.process.service.AuditRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/8/25
 */
@Service("auditRecordService")
public class AuditRecordServiceImpl implements AuditRecordService {

    private AuditRecordMapper auditRecordMapper;

    @Autowired
    public void setAuditRecordMapper(AuditRecordMapper auditRecordMapper) {
        this.auditRecordMapper = auditRecordMapper;
    }

    @Override
    public void insertSelective(AuditRecord record) {
        auditRecordMapper.insertSelective(record);
    }
}
