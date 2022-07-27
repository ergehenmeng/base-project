package com.eghm.service.pay.impl;

import com.eghm.dao.mapper.PayNotifyLogMapper;
import com.eghm.dao.model.PayNotifyLog;
import com.eghm.service.pay.PayNotifyLogService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/7/26
 */
@Service("payNotifyLogService")
@AllArgsConstructor
public class PayNotifyLogServiceImpl implements PayNotifyLogService {

    private final PayNotifyLogMapper payNotifyLogMapper;

    @Override
    @Async
    public void insert(PayNotifyLog log) {
        payNotifyLogMapper.insert(log);
    }
}
