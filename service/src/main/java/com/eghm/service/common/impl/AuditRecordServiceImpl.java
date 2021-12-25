package com.eghm.service.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.mapper.AuditRecordMapper;
import com.eghm.dao.model.AuditRecord;
import com.eghm.model.dto.audit.AuditQueryRequest;
import com.eghm.service.common.AuditRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void insert(AuditRecord auditRecord) {
        auditRecordMapper.insert(auditRecord);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateById(AuditRecord auditRecord) {
        auditRecordMapper.updateById(auditRecord);
    }

    @Override
    public AuditRecord getById(Long id) {
        return auditRecordMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public Page<AuditRecord> getByPage(AuditQueryRequest request) {
        LambdaQueryWrapper<AuditRecord> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, AuditRecord::getState, request.getState());
        wrapper.in(AuditRecord::getAuditType, request.getRoleList());
        return auditRecordMapper.selectPage(request.createPage(), wrapper);
    }
}
