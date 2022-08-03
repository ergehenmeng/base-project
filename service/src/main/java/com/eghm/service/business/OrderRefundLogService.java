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

    /**
     * 根据id查询退款记录
     * @param id id
     * @return 退款记录
     */
    OrderRefundLog selectById(Long id);

    /**
     * 根据id更新退款记录
     * @param log 退款记录
     * @return 1条
     */
    int updateById(OrderRefundLog log);
}
