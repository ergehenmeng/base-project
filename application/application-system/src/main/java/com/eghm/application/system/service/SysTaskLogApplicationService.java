package com.eghm.application.system.service;

import com.eghm.domain.system.model.SysTaskLog;
import com.eghm.domain.system.repository.SysTaskLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/9/11 11:18
 */
@Service
@AllArgsConstructor
public class SysTaskLogApplicationService {

    private final SysTaskLogRepository sysTaskLogRepository;

    /**
     * 添加定时任务执行日志
     *
     * @param log 日志信息
     */
    public void addTaskLog(SysTaskLog log) {
        sysTaskLogRepository.save(log);
    }

    /**
     * 定时任务错误信息详情
     *
     * @param id 主键
     * @return errorMsg字段有值
     */
    public String getErrorMsg(Long id) {
        return sysTaskLogRepository.getErrorMsg(id);
    }
}
