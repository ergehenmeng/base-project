package com.eghm.service.sys.impl;

import com.eghm.dao.mapper.SysOperationLogMapper;
import com.eghm.dao.model.SysOperationLog;
import com.eghm.model.dto.log.OperationQueryRequest;
import com.eghm.service.common.KeyGenerator;
import com.eghm.service.sys.OperationLogService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 操作日期
 *
 * @author 二哥很猛
 * @date 2019/1/15 17:55
 */
@Service("operationLogService")
public class OperationLogServiceImpl implements OperationLogService {

    private SysOperationLogMapper sysOperationLogMapper;

    private KeyGenerator keyGenerator;

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Autowired
    public void setSysOperationLogMapper(SysOperationLogMapper sysOperationLogMapper) {
        this.sysOperationLogMapper = sysOperationLogMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertOperationLog(SysOperationLog log) {
        log.setId(keyGenerator.generateKey());
        sysOperationLogMapper.insert(log);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<SysOperationLog> getByPage(OperationQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<SysOperationLog> list = sysOperationLogMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public String getResponseById(Long id) {
        return sysOperationLogMapper.getResponseById(id);
    }
}
