package com.eghm.application.system.service.impl;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.log.SmsLogQueryRequest;
import com.eghm.application.system.port.out.SmsLogQueryGateway;
import com.eghm.application.system.port.in.SmsLogService;
import com.eghm.domain.system.model.SmsLog;
import com.eghm.domain.system.repository.SmsLogRepository;
import com.eghm.application.shared.vo.operate.log.SmsLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 二哥很猛
 * @since 2019/8/16 18:47
 */
@AllArgsConstructor
@Service("smsLogService")
public class SmsLogServiceImpl implements SmsLogService {

    private final SmsLogRepository smsLogRepository;

    private final SmsLogQueryGateway smsLogQueryGateway;

    @Override
    public Page<SmsLogResponse> getByPage(SmsLogQueryRequest request) {
        return smsLogQueryGateway.getByPage(request.createPage(), request);
    }

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void addSmsLog(SmsLog smsLog) {
        smsLogRepository.save(smsLog);
    }
}
