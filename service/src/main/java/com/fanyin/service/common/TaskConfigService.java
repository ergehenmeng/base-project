package com.fanyin.service.common;

import com.fanyin.dao.model.business.TaskConfig;
import com.fanyin.model.dto.business.task.TaskQueryRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/9/6 15:19
 */
public interface TaskConfigService {

    /**
     * 获取所有开启的定时任务
     * @return 定时任务配置列表
     */
    List<TaskConfig> getAvailableList();

    /**
     * 分页查询定时任务列表
     * @param request 查询条件
     * @return 列表
     */
    PageInfo<TaskConfig> getByPage(TaskQueryRequest request);
}