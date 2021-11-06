package com.eghm.dao.mapper;

import com.eghm.dao.model.TaskConfig;
import com.eghm.model.dto.task.TaskQueryRequest;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface TaskConfigMapper {

    /**
     * 获取所有开启的定时任务
     * @return 定时任务配置列表
     */
    List<TaskConfig> getAvailableList();

    /**
     * 根据条件查询定时任务列表
     * @param request 查询条件
     * @return 列表
     */
    List<TaskConfig> getList(TaskQueryRequest request);
}