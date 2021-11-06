package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.TaskLog;
import com.eghm.model.dto.task.TaskLogQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface TaskLogMapper extends BaseMapper<TaskLog> {

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
    TaskLog getErrorMsg(@Param("id") Long id);
}