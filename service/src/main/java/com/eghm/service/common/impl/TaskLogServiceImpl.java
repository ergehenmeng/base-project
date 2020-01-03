package com.eghm.service.common.impl;

import com.eghm.dao.mapper.business.TaskLogMapper;
import com.eghm.dao.model.business.TaskLog;
import com.eghm.model.dto.business.task.TaskLogQueryRequest;
import com.eghm.service.common.TaskLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/9/11 11:18
 */
@Service("taskLogService")
public class TaskLogServiceImpl implements TaskLogService {

    @Autowired
    private TaskLogMapper taskLogMapper;

    @Override
    public void addTaskLog(TaskLog log) {
        taskLogMapper.insertSelective(log);
    }

    @Override
    public PageInfo<TaskLog> getByPage(TaskLogQueryRequest request) {
        PageHelper.startPage(request.getPage(), request.getPageSize());
        List<TaskLog> list = taskLogMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public TaskLog getErrorMsg(Integer id) {
        return taskLogMapper.getErrorMsg(id);
    }
}
