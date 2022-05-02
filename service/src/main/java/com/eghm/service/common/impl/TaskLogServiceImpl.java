package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.mapper.TaskLogMapper;
import com.eghm.dao.model.TaskLog;
import com.eghm.model.dto.task.TaskLogQueryRequest;
import com.eghm.service.common.TaskLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 二哥很猛
 * @date 2019/9/11 11:18
 */
@Service("taskLogService")
@AllArgsConstructor
public class TaskLogServiceImpl implements TaskLogService {

    private final TaskLogMapper taskLogMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addTaskLog(TaskLog log) {
        taskLogMapper.insert(log);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class,readOnly = true)
    public Page<TaskLog> getByPage(TaskLogQueryRequest request) {
        LambdaQueryWrapper<TaskLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, TaskLog::getState, request.getState());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper ->
                queryWrapper.like(TaskLog::getNid, request.getQueryName()).or()
                        .like(TaskLog::getBeanName, request.getQueryName()).or()
                        .like(TaskLog::getIp, request.getQueryName()));
        return taskLogMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public TaskLog getErrorMsg(Long id) {
        return taskLogMapper.getErrorMsg(id);
    }
}
