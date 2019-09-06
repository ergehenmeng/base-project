package com.fanyin.service.common;

import com.fanyin.dao.model.business.JobTask;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/9/6 15:19
 */
public interface JobTaskService {

    /**
     * 获取所有开启的定时任务
     * @return 定时任务配置列表
     */
    List<JobTask> getAvailableList();
}
