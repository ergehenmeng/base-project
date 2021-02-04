package com.eghm.service.common;

import com.eghm.dao.model.TaskConfig;
import com.eghm.model.dto.task.TaskEditRequest;
import com.eghm.model.dto.task.TaskQueryRequest;
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

    /**
     * 主键查询
     * @param id id
     * @return 定时任务配置信息
     */
    TaskConfig getById(Long id);

    /**
     * 编辑保存任务配置信息
     * @param request 配置信息
     */
    void editTaskConfig(TaskEditRequest request);
}
