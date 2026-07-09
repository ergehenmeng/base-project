package com.eghm.application.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.task.TaskQueryRequest;
import com.eghm.application.shared.vo.operate.task.SysTaskResponse;

/**
 * 定时任务查询网关
 *
 * @author 二哥很猛
 */
public interface SysTaskQueryService {

    /**
     * 分页查询
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<SysTaskResponse> getByPage(TaskQueryRequest request);
}
