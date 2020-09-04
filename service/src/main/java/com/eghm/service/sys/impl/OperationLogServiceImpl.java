package com.eghm.service.sys.impl;

import com.eghm.dao.mapper.system.SysOperationLogMapper;
import com.eghm.dao.model.system.SysOperationLog;
import com.eghm.model.dto.log.OperationQueryRequest;
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
@Transactional(rollbackFor = RuntimeException.class)
public class OperationLogServiceImpl implements OperationLogService {

    private SysOperationLogMapper sysOperationLogMapper;

    @Autowired
    public void setSysOperationLogMapper(SysOperationLogMapper sysOperationLogMapper) {
        this.sysOperationLogMapper = sysOperationLogMapper;
    }

    @Override
    public void insertOperationLog(SysOperationLog log) {
        sysOperationLogMapper.insertSelective(log);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<SysOperationLog> getByPage(OperationQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<SysOperationLog> list = sysOperationLogMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public String getResponseById(Integer id) {
        return sysOperationLogMapper.getResponseById(id);
    }
}
