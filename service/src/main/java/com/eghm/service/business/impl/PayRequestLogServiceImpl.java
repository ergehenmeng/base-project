package com.eghm.service.business.impl;

import com.eghm.mapper.PayRequestLogMapper;
import com.eghm.model.PayRequestLog;
import com.eghm.service.business.PayRequestLogService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 支付或退款请求记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-13
 */
@Service("payRequestLogService")
@AllArgsConstructor
public class PayRequestLogServiceImpl implements PayRequestLogService {

    private final PayRequestLogMapper payRequestLogMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    @Async
    public void insertLog(PayRequestLog requestLog) {
        payRequestLogMapper.insert(requestLog);
    }
}
