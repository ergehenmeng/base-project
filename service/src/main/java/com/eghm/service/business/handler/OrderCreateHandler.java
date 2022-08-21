package com.eghm.service.business.handler;

import com.eghm.model.dto.business.order.OrderCreateDTO;

/**
 * @author 二哥很猛
 * @date 2022/8/21
 */
public interface OrderCreateHandler {

    /**
     * 创建订单处理
     * @param dto 订单信息
     */
    void process(OrderCreateDTO dto);
}
