package com.eghm.service.sys;

import com.eghm.dto.ext.Page;
import com.eghm.dto.sys.task.TaskLogQueryRequest;
import com.eghm.vo.operate.log.SysTaskLogResponse;

/**
 * 定时任务日志查询端口
 *
 * @author 二哥很猛
 * @since 2019/9/11 11:18
 */
public interface SysTaskLogQueryGateway {

    Page<SysTaskLogResponse> getByPage(Page<SysTaskLogResponse> page, TaskLogQueryRequest request);
}
