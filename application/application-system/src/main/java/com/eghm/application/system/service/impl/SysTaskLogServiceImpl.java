package com.eghm.application.system.service.impl;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.task.TaskLogQueryRequest;
import com.eghm.application.system.port.out.SysTaskLogQueryGateway;
import com.eghm.application.system.port.in.SysTaskLogService;
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
public class SysTaskLogServiceImpl implements SysTaskLogService {

    private final SysTaskLogRepository sysTaskLogRepository;

    private final SysTaskLogQueryGateway sysTaskLogQueryGateway;

    @Override
    public Page<SysTaskLogResponse> getByPage(TaskLogQueryRequest request) {
        return sysTaskLogQueryGateway.getByPage(request.createPage(), request);
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
