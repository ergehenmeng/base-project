package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.task.TaskEditRequest;
import com.eghm.dto.task.TaskQueryRequest;
import com.eghm.model.SysTask;
import com.eghm.vo.task.SysTaskResponse;

/**
 * @author 二哥很猛
 * @since 2019/9/6 15:19
 */
public interface SysTaskService {

    /**
     * 分页查询定时任务列表
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<SysTaskResponse> getByPage(TaskQueryRequest request);

    /**
     * 主键查询
     *
     * @param id id
     * @return 定时任务配置信息
     */
    SysTask getById(Long id);

    /**
     * 编辑保存任务配置信息
     *
     * @param request 配置信息
     */
    void update(TaskEditRequest request);

    /**
     * 运行一次定时任务
     *
     * @param id   任务配置id
     * @param args 任务参数
     */
    void execute(Long id, String args);
}
