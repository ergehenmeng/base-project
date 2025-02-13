package com.eghm.state.machine.impl.item;

import cn.hutool.core.collection.CollUtil;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.OrderState;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.state.machine.ActionHandler;
import com.eghm.state.machine.context.PayNotifyContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
public abstract class AbstractItemOrderPayNotifyHandler implements ActionHandler<PayNotifyContext> {

    private final OrderService orderService;

    @Override
    public void doAction(PayNotifyContext context) {
        List<Order> orderList = orderService.getByTradeNoList(context.getTradeNo());
        this.before(context, orderList);
        this.doProcess(context, orderList);
        this.after(context, orderList);
    }

    /**
     * 订单信息校验
     *
     * @param context   异步通知上下文
     * @param orderList 订单信息
     */
    private void before(PayNotifyContext context, List<Order> orderList) {
        if (CollUtil.isEmpty(orderList)) {
            log.error("根据交易单号未查询到订单 [{}] [{}]", context.getTradeNo(), context.getTradeType());
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND);
        }
        for (Order order : orderList) {
            if (order.getState() != OrderState.PROGRESS && order.getState() != OrderState.UN_PAY) {
                log.error("订单状态已更改,无须更新支付状态 [{}] [{}]", order.getOrderNo(), order.getState());
                throw new BusinessException(ErrorCode.ORDER_PAID);
            }
        }
    }

    /**
     * 处理支付通知 主逻辑
     *
     * @param context   支付成功或失败上下文
     * @param orderList 订单列表
     */
    protected abstract void doProcess(PayNotifyContext context, List<Order> orderList);

    /**
     * 支付成功后处理
     *
     * @param context   支付成功或失败上下文
     * @param orderList 订单列表
     */
    protected void after(PayNotifyContext context, List<Order> orderList) {
        log.info("零售订单异步回调处理结束 [{}] [{}] [{}]", context.getTradeNo(), context.getTradeType(), orderList.size());
    }
}
