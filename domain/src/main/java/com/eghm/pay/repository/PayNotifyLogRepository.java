package com.eghm.pay.repository;

import com.eghm.pay.model.PayNotifyLog;

/**
 * 支付异步通知日志仓储接口
 *
 * @author 二哥很猛
 * @since 2022/7/26
 */
public interface PayNotifyLogRepository {

    /**
     * 保存异步通知日志
     *
     * @param log 异步通知日志
     */
    void save(PayNotifyLog log);

    /**
     * 根据id查询异步回调信息
     *
     * @param id id
     * @return 异步回调
     */
    PayNotifyLog findById(Long id);

    /**
     * 修改异步回调状态
     *
     * @param id id
     */
    void markPlaybackSuccess(Long id);
}
