package com.fanyin.service.common.impl;

import com.fanyin.dao.mapper.business.JobTaskMapper;
import com.fanyin.dao.model.business.JobTask;
import com.fanyin.model.dto.business.task.TaskQueryRequest;
import com.fanyin.service.common.JobTaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/9/6 15:19
 */
@Service("jobTaskService")
public class JobTaskServiceImpl implements JobTaskService {

    @Autowired
    private JobTaskMapper jobTaskMapper;

    @Override
    public List<JobTask> getAvailableList() {
        return jobTaskMapper.getAvailableList();
    }

    @Override
    public PageInfo<JobTask> getByPage(TaskQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<JobTask> list = jobTaskMapper.getList(request);
        return new PageInfo<>(list);
    }
}
