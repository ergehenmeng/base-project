package com.eghm.process.service.impl;

import com.eghm.dao.mapper.business.AuditRecordMapper;
import com.eghm.dao.model.business.AuditRecord;
import com.eghm.model.dto.business.audit.AuditQueryRequest;
import com.eghm.process.service.AuditRecordService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void insertSelective(AuditRecord record) {
        auditRecordMapper.insertSelective(record);
    }

    @Override
    public void updateSelective(AuditRecord record) {
        auditRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public AuditRecord getById(Integer id) {
        return auditRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<AuditRecord> getByPage(AuditQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<AuditRecord> auditList = auditRecordMapper.getAuditList(request.getRoleList(), request.getState());
        return new PageInfo<>(auditList);
    }
}
