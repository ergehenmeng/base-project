package com.fanyin.service.system.impl;

import com.fanyin.dto.system.log.OperationQueryRequest;
import com.fanyin.mapper.system.SystemOperationLogMapper;
import com.fanyin.model.system.SystemOperationLog;
import com.fanyin.service.system.OperationLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 操作日期
 * @author 二哥很猛
 * @date 2019/1/15 17:55
 */
@Service("operationLogService")
@Transactional(rollbackFor = RuntimeException.class)
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private SystemOperationLogMapper systemOperationLogMapper;

    @Override
    public void insertOperationLog(SystemOperationLog log) {
        systemOperationLogMapper.insertSelective(log);
    }

    @Override
    public PageInfo<SystemOperationLog> getByPage(OperationQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<SystemOperationLog> list = systemOperationLogMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public String getResponseById(Integer id) {
        return systemOperationLogMapper.getResponseById(id);
    }
}
