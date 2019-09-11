package com.fanyin.service.common.impl;

import com.fanyin.dao.mapper.business.TaskConfigMapper;
import com.fanyin.dao.model.business.TaskConfig;
import com.fanyin.model.dto.business.task.TaskQueryRequest;
import com.fanyin.service.common.TaskConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/9/6 15:19
 */
@Service("taskConfigService")
@Transactional(rollbackFor = RuntimeException.class,readOnly = true)
public class TaskConfigServiceImpl implements TaskConfigService {

    @Autowired
    private TaskConfigMapper taskConfigMapper;

    @Override
    public List<TaskConfig> getAvailableList() {
        return taskConfigMapper.getAvailableList();
    }

    @Override
    public PageInfo<TaskConfig> getByPage(TaskQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<TaskConfig> list = taskConfigMapper.getList(request);
        return new PageInfo<>(list);
    }
}
