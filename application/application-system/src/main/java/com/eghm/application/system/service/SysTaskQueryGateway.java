package com.eghm.application.system.service;

import com.eghm.dto.ext.Page;
import com.eghm.dto.sys.task.TaskQueryRequest;
import com.eghm.vo.operate.task.SysTaskResponse;

/**
 * 定时任务查询网关
 *
 * @author 二哥很猛
 */
public interface SysTaskQueryGateway {

    /**
     * 分页查询
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<SysTaskResponse> getByPage(TaskQueryRequest request);
}
