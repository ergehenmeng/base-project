package com.eghm.service.sys.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.mapper.SysOperationLogMapper;
import com.eghm.dao.model.SysOperationLog;
import com.eghm.model.dto.log.OperationQueryRequest;
import com.eghm.service.sys.OperationLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 操作日期
 *
 * @author 二哥很猛
 * @date 2019/1/15 17:55
 */
@Service("operationLogService")
@AllArgsConstructor
public class OperationLogServiceImpl implements OperationLogService {

    private final SysOperationLogMapper sysOperationLogMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertOperationLog(SysOperationLog log) {
        sysOperationLogMapper.insert(log);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public Page<SysOperationLog> getByPage(OperationQueryRequest request) {
        return sysOperationLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public String getResponseById(Long id) {
        return sysOperationLogMapper.getResponseById(id);
    }
}
