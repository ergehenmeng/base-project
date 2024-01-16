package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.task.TaskLogQueryRequest;
import com.eghm.mapper.SysTaskLogMapper;
import com.eghm.model.SysTaskLog;
import com.eghm.service.common.SysTaskLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/9/11 11:18
 */
@Service("sysTaskLogService")
@AllArgsConstructor
public class SysTaskLogServiceImpl implements SysTaskLogService {

    private final SysTaskLogMapper sysTaskLogMapper;

    @Override
    public Page<SysTaskLog> getByPage(TaskLogQueryRequest request) {
        LambdaQueryWrapper<SysTaskLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, SysTaskLog::getState, request.getState());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper ->
                queryWrapper.like(SysTaskLog::getMethodName, request.getQueryName()).or()
                        .like(SysTaskLog::getBeanName, request.getQueryName()).or()
                        .like(SysTaskLog::getIp, request.getQueryName()));
        return sysTaskLogMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void addTaskLog(SysTaskLog log) {
        sysTaskLogMapper.insert(log);
    }

    @Override
    public String getErrorMsg(Long id) {
        return sysTaskLogMapper.getErrorMsg(id);
    }
}
