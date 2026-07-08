package com.eghm.sys.repository;

import com.eghm.sys.model.ManageLog;

/**
 * 管理端操作日志仓储接口
 *
 * @author 二哥很猛
 * @since 2019/1/15 17:55
 */
public interface ManageLogRepository {

    /**
     * 添加操作日志
     *
     * @param log 日志
     */
    void save(ManageLog log);
}
