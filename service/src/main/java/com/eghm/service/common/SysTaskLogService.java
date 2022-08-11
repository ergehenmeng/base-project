package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.SysTaskLog;
import com.eghm.model.dto.task.TaskLogQueryRequest;

/**
 * @author 二哥很猛
 * @date 2019/9/11 11:18
 */
public interface SysTaskLogService {

    /**
     * 添加定时任务执行日志
     * @param log 日志信息
     */
    void addTaskLog(SysTaskLog log);

    /**
     * 根据条件查询定时任务日志信息
     * @param request 查询条件
     * @return 列表
     */
    Page<SysTaskLog> getByPage(TaskLogQueryRequest request);

    /**
     * 定时任务错误信息详情
     * @param id 主键
     * @return errorMsg字段有值
     */
    String getErrorMsg(Long id);
}
