package com.eghm.application.system.service.impl;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.task.TaskLogQueryRequest;
import com.eghm.application.system.query.SysTaskLogQueryService;
import com.eghm.application.system.service.SysTaskLogApplicationService;
import com.eghm.domain.system.model.SysTaskLog;
import com.eghm.domain.system.repository.SysTaskLogRepository;
import com.eghm.application.shared.vo.operate.log.SysTaskLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/9/11 11:18
 */
@AllArgsConstructor
@Service("sysTaskLogService")
public class SysTaskLogApplicationServiceImpl implements SysTaskLogApplicationService {

    private final SysTaskLogRepository sysTaskLogRepository;

    private final SysTaskLogQueryService sysTaskLogQueryService;

    @Override
    public Page<SysTaskLogResponse> getByPage(TaskLogQueryRequest request) {
        return sysTaskLogQueryService.getByPage(request.createPage(), request);
    }

    @Override
    public void addTaskLog(SysTaskLog log) {
        sysTaskLogRepository.save(log);
    }

    @Override
    public String getErrorMsg(Long id) {
        return sysTaskLogRepository.getErrorMsg(id);
    }
}
