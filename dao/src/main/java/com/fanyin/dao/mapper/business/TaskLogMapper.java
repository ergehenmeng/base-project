package com.fanyin.dao.mapper.business;

import com.fanyin.dao.model.business.TaskLog;
import com.fanyin.model.dto.business.task.TaskLogQueryRequest;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface TaskLogMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(TaskLog record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    TaskLog selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(TaskLog record);

    /**
     * 根据条件查询定时任务日志列表
     * @param request 查询条件
     * @return 列表
     */
    List<TaskLog> getList(TaskLogQueryRequest request);
}