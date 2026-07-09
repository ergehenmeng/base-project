package com.eghm.application.system.service;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.task.TaskLogQueryRequest;
import com.eghm.domain.system.model.SysTaskLog;
import com.eghm.application.shared.vo.operate.log.SysTaskLogResponse;

/**
 * @author 二哥很猛
 * @since 2019/9/11 11:18
 */
public interface SysTaskLogApplicationService {

    /**
     * 根据条件查询定时任务日志信息
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<SysTaskLogResponse> getByPage(TaskLogQueryRequest request);

    /**
     * 添加定时任务执行日志
     *
     * @param log 日志信息
     */
    void addTaskLog(SysTaskLog log);

    /**
     * 定时任务错误信息详情
     *
     * @param id 主键
     * @return errorMsg字段有值
     */
    String getErrorMsg(Long id);
}
