package com.eghm.service.business;

import com.eghm.dao.model.HomestayOrder;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
public interface HomestayOrderService {

    /**
     * 插入民宿订单
     * @param order 订单信息
     */
    void insert(HomestayOrder order);
}
