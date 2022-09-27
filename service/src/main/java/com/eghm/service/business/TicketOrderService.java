package com.eghm.service.business;

import com.eghm.model.TicketOrder;

/**
 * @author 二哥很猛
 * @date 2022/7/12
 */
public interface TicketOrderService {

    /**
     * 插入门票订单信息
     * @param order 门票订单
     */
    void insert(TicketOrder order);

    /**
     * 根据订单编号查询订单订单信息
     * @param orderNo 订单编号
     * @return 门票订单信息
     */
    TicketOrder selectByOrderNo(String orderNo);
}
