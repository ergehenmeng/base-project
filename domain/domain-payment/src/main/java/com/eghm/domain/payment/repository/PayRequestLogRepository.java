package com.eghm.domain.payment.repository;

import com.eghm.domain.payment.model.PayRequestLog;

/**
 * 支付或退款请求记录仓储接口
 *
 * @author 二哥很猛
 * @since 2023-06-13
 */
public interface PayRequestLogRepository {

    /**
     * 保存支付或退款请求记录
     *
     * @param requestLog 请求记录
     */
    void save(PayRequestLog requestLog);
}
