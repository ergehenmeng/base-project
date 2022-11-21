package com.eghm.service.business.handler;

import com.eghm.service.business.handler.dto.OrderCancelContext;
import com.eghm.state.machine.ActionHandler;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
public interface OrderExpireHandler extends ActionHandler<OrderCancelContext> {

}
