package com.eghm.application.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.task.TaskLogQueryRequest;
import com.eghm.application.shared.vo.operate.log.SysTaskLogResponse;

/**
 * 定时任务日志查询端口
 *
 * @author 二哥很猛
 * @since 2019/9/11 11:18
 */
public interface SysTaskLogQueryService {

    Page<SysTaskLogResponse> getByPage(Page<SysTaskLogResponse> page, TaskLogQueryRequest request);
}
