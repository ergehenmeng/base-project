package com.eghm.service.business.handler.state;

import com.eghm.service.business.handler.context.OrderCancelContext;
import com.eghm.state.machine.ActionHandler;

/**
 * 订单自动取消
 *
 * @author 二哥很猛
 * @since 2022/8/20
 */
public interface OrderAutoCancelHandler extends ActionHandler<OrderCancelContext> {

}
