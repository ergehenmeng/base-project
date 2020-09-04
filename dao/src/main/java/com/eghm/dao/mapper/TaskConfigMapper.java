package com.eghm.dao.mapper;

import com.eghm.dao.model.TaskConfig;
import com.eghm.model.dto.task.TaskQueryRequest;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface TaskConfigMapper {

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    TaskConfig selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(TaskConfig record);

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