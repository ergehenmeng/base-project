package com.eghm.service.common.impl;

import com.eghm.dao.mapper.TaskLogMapper;
import com.eghm.dao.model.TaskLog;
import com.eghm.model.dto.task.TaskLogQueryRequest;
import com.eghm.service.common.KeyGenerator;
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
public class TaskLogServiceImpl implements TaskLogService {

    private TaskLogMapper taskLogMapper;

    private KeyGenerator keyGenerator;

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Autowired
    public void setTaskLogMapper(TaskLogMapper taskLogMapper) {
        this.taskLogMapper = taskLogMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addTaskLog(TaskLog log) {
        log.setId(keyGenerator.generateKey());
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
    public TaskLog getErrorMsg(Long id) {
        return taskLogMapper.getErrorMsg(id);
    }
}
