package com.eghm.service.system.impl;

import com.eghm.dao.mapper.system.SystemOperationLogMapper;
import com.eghm.dao.model.system.SystemOperationLog;
import com.eghm.model.dto.system.log.OperationQueryRequest;
import com.eghm.service.system.OperationLogService;
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
@Transactional(rollbackFor = RuntimeException.class)
public class OperationLogServiceImpl implements OperationLogService {

    private SystemOperationLogMapper systemOperationLogMapper;

    @Autowired
    public void setSystemOperationLogMapper(SystemOperationLogMapper systemOperationLogMapper) {
        this.systemOperationLogMapper = systemOperationLogMapper;
    }

    @Override
    public void insertOperationLog(SystemOperationLog log) {
        systemOperationLogMapper.insertSelective(log);
    }

    @Override
    public PageInfo<SystemOperationLog> getByPage(OperationQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<SystemOperationLog> list = systemOperationLogMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public String getResponseById(Integer id) {
        return systemOperationLogMapper.getResponseById(id);
    }
}
