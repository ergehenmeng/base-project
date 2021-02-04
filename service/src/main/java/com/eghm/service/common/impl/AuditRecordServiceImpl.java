package com.eghm.service.common.impl;

import com.eghm.dao.mapper.AuditRecordMapper;
import com.eghm.dao.model.AuditRecord;
import com.eghm.model.dto.audit.AuditQueryRequest;
import com.eghm.service.common.AuditRecordService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertSelective(AuditRecord record) {
        auditRecordMapper.insertSelective(record);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateSelective(AuditRecord record) {
        auditRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public AuditRecord getById(Long id) {
        return auditRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<AuditRecord> getByPage(AuditQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<AuditRecord> auditList = auditRecordMapper.getAuditList(request.getRoleList(), request.getState());
        return new PageInfo<>(auditList);
    }
}
