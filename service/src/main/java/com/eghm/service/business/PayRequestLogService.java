package com.eghm.service.business;

import com.eghm.model.PayRequestLog;

/**
 * <p>
 * 支付或退款请求记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-13
 */
public interface PayRequestLogService {

    /**
     * 添加请求日志
     * @param requestLog 请求日志
     */
    void insertLog(PayRequestLog requestLog);
}
