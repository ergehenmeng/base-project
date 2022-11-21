package com.eghm.service.business.handler;

import com.eghm.common.enums.event.IEvent;
import com.eghm.service.business.handler.dto.OrderCreateContext;
import com.eghm.state.machine.ActionHandler;

/**
 * @author 二哥很猛
 * @date 2022/8/21
 */
public interface OrderCreateHandler extends ActionHandler<OrderCreateContext> {

    /**
     * 创建订单处理
     * @param context 订单信息
     */
    void process(OrderCreateContext context);


    @Override
    default void execute(Integer from, Integer to, IEvent event, OrderCreateContext context) {
        OrderCreateContext dto = new OrderCreateContext();
        dto.setFrom(from);
        dto.setTo(to);
        this.process(dto);
    }
}
