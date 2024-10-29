package com.eghm.service.sys.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.log.SmsLogQueryRequest;
import com.eghm.mapper.SmsLogMapper;
import com.eghm.model.SmsLog;
import com.eghm.service.sys.SmsLogService;
import com.eghm.vo.log.SmsLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 二哥很猛
 * @since 2019/8/16 18:47
 */
@Service("smsLogService")
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
