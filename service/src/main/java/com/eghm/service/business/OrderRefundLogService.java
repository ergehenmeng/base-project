package com.eghm.service.business;

import com.eghm.dao.model.OrderRefundLog;

/**
 * @author 二哥很猛
 * @date 2022/8/1
 */
public interface OrderRefundLogService {

    /**
     * 新增退款记录
     * @param log 退款记录
     */
    void insert(OrderRefundLog log);
}
