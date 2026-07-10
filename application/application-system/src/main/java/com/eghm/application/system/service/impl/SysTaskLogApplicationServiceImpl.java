package com.eghm.application.system.service.impl;

import com.eghm.application.system.service.SysTaskLogApplicationService;
import com.eghm.domain.system.model.SysTaskLog;
import com.eghm.domain.system.repository.SysTaskLogRepository;
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

    @Override
    public void addTaskLog(SysTaskLog log) {
        sysTaskLogRepository.save(log);
    }

    @Override
    public String getErrorMsg(Long id) {
        return sysTaskLogRepository.getErrorMsg(id);
    }
}
