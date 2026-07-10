package com.eghm.application.system.service;

import com.eghm.domain.system.model.ManageLog;
import com.eghm.domain.system.repository.ManageLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 操作日期
 *
 * @author 二哥很猛
 * @since 2019/1/15 17:55
 */
@Service
@AllArgsConstructor
public class ManageLogApplicationService {

    private final ManageLogRepository manageLogRepository;

    /**
     * 添加操作日志
     *
     * @param log 日志
     */
    public void insertManageLog(ManageLog log) {
        manageLogRepository.save(log);
    }
}
