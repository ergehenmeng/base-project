package com.eghm.service.business.handler.impl.product;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.StateMachineType;
import com.eghm.common.enums.event.IEvent;
import com.eghm.common.enums.ref.OrderState;
import com.eghm.common.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.ProductService;
import com.eghm.service.business.handler.PayNotifyHandler;
import com.eghm.service.business.handler.dto.PayNotifyContext;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.OrderVO;
import com.eghm.utils.TransactionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 普通商品订单支付通知
 * 1. 会存在多个商品关联一个支付流水号
 * @author 二哥很猛
 * @date 2022/9/9
 */
@Service("productPayNotifyHandler")
@AllArgsConstructor
@Slf4j
public class ProductPayNotifyHandler implements PayNotifyHandler {

    private final OrderService orderService;

    private final AggregatePayService aggregatePayService;

    private final ProductService productService;

    private final TransactionTemplate transactionTemplate;

    @Override
    public IEvent getEvent() {
        return null;
    }

    @Override
    public StateMachineType getStateMachineType() {
        return null;
    }

    @Override
    @Async
    public void doAction(PayNotifyContext context) {
        List<Order> orderList = orderService.selectByOutTradeNoList(context.getOutTradeNo());
        this.before(orderList);

        TradeType tradeType = TradeType.valueOf(orderList.get(0).getPayType().name());
        OrderVO vo = aggregatePayService.queryOrder(tradeType, context.getOutTradeNo());
        boolean payState = vo.getTradeState() == TradeState.SUCCESS || vo.getTradeState() == TradeState.TRADE_SUCCESS;
        List<String> orderNoList = orderList.stream().map(Order::getOrderNo).collect(Collectors.toList());

        TransactionUtil.manualCommit(() -> {
            orderService.updateState(orderNoList, OrderState.UN_USED, OrderState.UN_PAY, OrderState.PROGRESS);
            productService.updateSaleNum(orderNoList);
        });

        log.info("订单支付异步处理结果 [{}] [{}] [{}]", context.getOutTradeNo(), payState, orderNoList);
    }

    /**
     * 订单信息校验
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
}
