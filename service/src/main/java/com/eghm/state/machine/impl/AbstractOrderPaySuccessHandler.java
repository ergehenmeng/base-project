package com.eghm.state.machine.impl;

import com.eghm.dto.ext.OrderPayNotify;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.eghm.enums.ref.VisitorState;
import com.eghm.model.Order;
import com.eghm.mq.service.MessageService;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.state.machine.ActionHandler;
import com.eghm.state.machine.context.PayNotifyContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 支付异步回调 成功
 * 注意: 该接口为抽象模板方法,只支持单商品下单的异步通知, 由于零售涉及购物车下单, 因此该方法不支持零售, 请直接实现 {@link ActionHandler} 接口
 *
 * @author 二哥很猛
 * @since 2022/8/20
 */
@AllArgsConstructor
@Slf4j
public abstract class AbstractOrderPaySuccessHandler implements ActionHandler<PayNotifyContext> {

    private final OrderService orderService;

    private final AccountService accountService;

    private final MessageService messageService;

    private final OrderVisitorService orderVisitorService;

    @Override
    public void doAction(PayNotifyContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        this.doProcess(context, order);
        this.after(context, order);
    }

    /**
     * 订单成功
     *
     * @param order 订单信息
     */
    protected void doProcess(PayNotifyContext context, Order order) {
        orderService.paySuccess(order.getOrderNo(), order.getProductType().generateVerifyNo(), context.getSuccessTime(), OrderState.UN_USED, PayType.valueOf(context.getTradeType().name()));
    }

    /**
     * 订单异步通知后置处理
     *
     * @param context 支付成功异步通知
     * @param order   订单信息
     */
    protected void after(PayNotifyContext context, Order order) {
        log.info("订单支付成功, 更新游客和冻结金额 [{}] [{}] [{}]", context.getOrderNo(), context.getTradeNo(), context.getAmount());
        orderVisitorService.updateVisitor(order.getOrderNo(), VisitorState.PAID);
        accountService.paySuccessAddFreeze(order);
        this.sendPaySuccessMessage(context, order);
    }

    /**
     * 发送支付成功的mq消息,方便后续统计分析处理等
     *
     * @param context 异步接受到支付成功的信息
     * @param order 订单信息
     */
    protected void sendPaySuccessMessage(PayNotifyContext context, Order order) {
        log.info("订单支付成功, 发送支付成功MQ [{}]", context.getOrderNo());
        OrderPayNotify payNotify = new OrderPayNotify();
        payNotify.setAmount(context.getAmount());
        payNotify.setMerchantId(order.getMerchantId());
        payNotify.setOrderNo(order.getOrderNo());
        payNotify.setProductId(getProductId(order));
        payNotify.setProductType(order.getProductType());
        payNotify.setStoreId(order.getStoreId());
        messageService.send(ExchangeQueue.ORDER_PAY_SUCCESS, payNotify);
    }

    /**
     * 根据订单信息获取产品id
     *
     * @param order 订单信息
     * @return 商品id
     */
    protected Long getProductId(Order order) {
        return null;
    }

}
