package com.fanyin.service.common.impl;

import com.fanyin.dao.mapper.business.TaskLogMapper;
import com.fanyin.dao.model.business.TaskLog;
import com.fanyin.model.dto.business.task.TaskLogQueryRequest;
import com.fanyin.service.common.TaskLogService;
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
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<TaskLog> list = taskLogMapper.getList(request);
        return new PageInfo<>(list);
    }
}
