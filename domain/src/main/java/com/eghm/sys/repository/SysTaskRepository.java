package com.eghm.sys.repository;

import com.eghm.sys.model.SysTask;

/**
 * 定时任务仓储
 *
 * @author 二哥很猛
 */
public interface SysTaskRepository {

    /**
     * 根据id查询任务
     *
     * @param id 主键
     * @return 任务
     */
    SysTask findById(Long id);

    /**
     * 更新任务
     *
     * @param task 任务
     */
    void update(SysTask task);
}
