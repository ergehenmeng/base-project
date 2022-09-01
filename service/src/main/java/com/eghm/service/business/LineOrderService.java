package com.eghm.service.business;

import com.eghm.dao.model.LineOrder;

/**
 * @author 二哥很猛
 * @date 2022/9/1
 */
public interface LineOrderService {

    /**
     * 插入线路订单
     * @param order 订单信息
     */
    void insert(LineOrder order);

    /**
     * 根据订单编号查询线路订单(未删除的订单)
     * @param orderNo 订单编号
     * @return 线路订单
     */
    LineOrder selectByOrderNo(String orderNo);
}
