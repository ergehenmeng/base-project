package com.eghm.service.business.handler.state.impl;

import com.eghm.model.Order;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.service.business.handler.context.RefundCancelContext;
import com.eghm.service.business.handler.state.RefundCancelHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 退款异步回调
 *
 * @author 二哥很猛
 * @since 2022/8/20
 */
@AllArgsConstructor
@Slf4j
public abstract class AbstractOrderRefundCancelHandler implements RefundCancelHandler {

    private final OrderService orderService;

    private final OrderRefundLogService orderRefundLogService;

    private final VerifyLogService verifyLogService;

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void doAction(RefundCancelContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());

    }



}
