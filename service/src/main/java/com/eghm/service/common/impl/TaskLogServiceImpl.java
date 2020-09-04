package com.eghm.service.common.impl;

import com.eghm.dao.mapper.TaskLogMapper;
import com.eghm.dao.model.TaskLog;
import com.eghm.model.dto.task.TaskLogQueryRequest;
import com.eghm.service.common.TaskLogService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/9/11 11:18
 */
@Service("taskLogService")
@Transactional(rollbackFor = RuntimeException.class)
public class TaskLogServiceImpl implements TaskLogService {

    private TaskLogMapper taskLogMapper;

    @Autowired
    public void setTaskLogMapper(TaskLogMapper taskLogMapper) {
        this.taskLogMapper = taskLogMapper;
    }

    @Override
    public void addTaskLog(TaskLog log) {
        taskLogMapper.insertSelective(log);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class,readOnly = true)
    public PageInfo<TaskLog> getByPage(TaskLogQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<TaskLog> list = taskLogMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class,readOnly = true)
    public TaskLog getErrorMsg(Integer id) {
        return taskLogMapper.getErrorMsg(id);
    }
}
