package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.OrderState;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.business.handler.state.PayNotifyHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 普通商品订单支付通知(零售专用)
 * 说明:
 * 1. 会存在多个商品关联一个支付流水号
 *
 * @author 二哥很猛
 * @since 2022/9/9
 */
@AllArgsConstructor
@Slf4j
public abstract class AbstractItemOrderPayNotifyHandler implements PayNotifyHandler {

    private final OrderService orderService;

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void doAction(PayNotifyContext context) {
        List<Order> orderList = orderService.selectByTradeNoList(context.getTradeNo());
        this.before(orderList);
        List<String> orderNoList = orderList.stream().map(Order::getOrderNo).collect(Collectors.toList());
        this.doProcess(context, orderNoList);
        this.after(context, orderList);
    }

    /**
     * 订单信息校验
     *
     * @param orderList 订单信息
     */
    private void before(List<Order> orderList) {
        for (Order order : orderList) {
            if (order.getState() != OrderState.PROGRESS) {
                log.error("订单状态已更改,无须更新支付状态 [{}] [{}]", order.getOrderNo(), order.getState());
                throw new BusinessException(ErrorCode.ORDER_PAID);
            }
        }
    }

    /**
     * 处理支付通知 主逻辑
     *
     * @param context 支付成功或失败上下文
     * @param orderNoList 订单号列表
     */
    protected abstract void doProcess(PayNotifyContext context, List<String> orderNoList);


    /**
     * 支付成功后处理
     *
     * @param context 支付成功或失败上下文
     * @param orderList 订单列表
     */
    protected void after(PayNotifyContext context, List<Order> orderList) {
        log.info("零售订单异步回调处理结束 [{}] [{}] [{}]", context.getOrderNo(), context.getTradeType(), orderList.size());
    }
}
