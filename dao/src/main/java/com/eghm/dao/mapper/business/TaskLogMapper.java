package com.eghm.dao.mapper.business;

import com.eghm.dao.model.business.TaskLog;
import com.eghm.model.dto.task.TaskLogQueryRequest;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 定时任务错误信息详情
     * @param id 主键
     * @return errorMsg字段有值
     */
    TaskLog getErrorMsg(@Param("id") Integer id);
}