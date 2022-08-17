package com.eghm.service.business;

import com.eghm.dao.model.Order;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
public interface OrderService {

    /**
     * 添加主订单信息
     * @param order 订单信息
     */
    void insert(Order order);
}
