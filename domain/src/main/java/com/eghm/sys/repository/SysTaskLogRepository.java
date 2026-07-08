package com.eghm.sys.repository;

import com.eghm.sys.model.SysTaskLog;

/**
 * 定时任务日志仓储接口
 *
 * @author 二哥很猛
 * @since 2019/9/11 11:18
 */
public interface SysTaskLogRepository {

    /**
     * 添加定时任务执行日志
     *
     * @param log 日志信息
     */
    void save(SysTaskLog log);

    /**
     * 定时任务错误信息详情
     *
     * @param id 主键
     * @return errorMsg字段有值
     */
    String getErrorMsg(Long id);
}
