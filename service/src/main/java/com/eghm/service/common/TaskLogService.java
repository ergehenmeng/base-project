package com.eghm.service.common;

import com.eghm.dao.model.TaskLog;
import com.eghm.model.dto.task.TaskLogQueryRequest;
import com.github.pagehelper.PageInfo;

/**
 * @author 二哥很猛
 * @date 2019/9/11 11:18
 */
public interface TaskLogService {

    /**
     * 添加定时任务执行日志
     * @param log 日志信息
     */
    void addTaskLog(TaskLog log);

    /**
     * 根据条件查询定时任务日志信息
     * @param request 查询条件
     * @return 列表
     */
    PageInfo<TaskLog> getByPage(TaskLogQueryRequest request);

    /**
     * 定时任务错误信息详情
     * @param id 主键
     * @return errorMsg字段有值
     */
    TaskLog getErrorMsg(Integer id);
}
