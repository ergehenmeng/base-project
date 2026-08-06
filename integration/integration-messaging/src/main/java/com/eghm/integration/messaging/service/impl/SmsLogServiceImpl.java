package com.eghm.integration.messaging.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.integration.messaging.dto.SmsLogQueryRequest;
import com.eghm.integration.messaging.mapper.SmsLogMapper;
import com.eghm.integration.messaging.entity.SmsLog;
import com.eghm.integration.messaging.service.SmsLogService;
import com.eghm.integration.messaging.vo.SmsLogResponse;
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
public class SmsLogServiceImpl implements SmsLogService {

    private final SmsLogMapper smsLogMapper;

    @Override
    public Page<SmsLogResponse> getByPage(SmsLogQueryRequest request) {
        return smsLogMapper.getByPage(request.createPage(), request);
    }

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void addSmsLog(SmsLog smsLog) {
        smsLogMapper.insert(smsLog);
    }

}
