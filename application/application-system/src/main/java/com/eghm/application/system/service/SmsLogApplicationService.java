package com.eghm.application.system.service;

import com.eghm.domain.system.model.SmsLog;
import com.eghm.domain.system.repository.SmsLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 二哥很猛
 * @since 2019/8/16 18:47
 */
@Service
@AllArgsConstructor
public class SmsLogApplicationService {

    private final SmsLogRepository smsLogRepository;

    /**
     * 添加短信记录
     *
     * @param smsLog smsLog
     */
    @Async
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void addSmsLog(SmsLog smsLog) {
        smsLogRepository.save(smsLog);
    }
}
