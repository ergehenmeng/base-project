package com.eghm.state.machine.impl.item;

import com.eghm.common.AlarmService;
import com.eghm.dto.business.account.score.ScoreAccountDTO;
import com.eghm.dto.ext.ItemOrderPayNotify;
import com.eghm.dto.ext.OrderPayNotify;
import com.eghm.enums.*;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.model.GroupBooking;
import com.eghm.model.ItemGroupOrder;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.mq.service.MessageService;
import com.eghm.service.business.*;
import com.eghm.state.machine.context.PayNotifyContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 普通商品订单支付通知
 * 说明:
 * 1. 会存在多个商品关联一个支付流水号
 * 2. 该接口是
 *
 * @author 二哥很猛
 * @since 2022/9/9
 */
@Service("itemOrderPaySuccessHandler")
@Slf4j
public class ItemOrderPaySuccessHandler extends AbstractItemOrderPayNotifyHandler {

    private final ItemService itemService;

    private final AlarmService alarmService;

    private final OrderService orderService;

    private final AccountService accountService;

    private final MessageService messageService;

    private final ItemOrderService itemOrderService;

    private final GroupBookingService groupBookingService;

    private final ScoreAccountService scoreAccountService;

    private final ItemGroupOrderService itemGroupOrderService;

    public ItemOrderPaySuccessHandler(ScoreAccountService scoreAccountService, OrderService orderService, ItemService itemService, ItemGroupOrderService itemGroupOrderService,
                                      GroupBookingService groupBookingService, AlarmService alarmService, AccountService accountService, ItemOrderService itemOrderService,
                                      MessageService messageService) {
        super(orderService);
        this.itemService = itemService;
        this.orderService = orderService;
        this.itemGroupOrderService = itemGroupOrderService;
        this.groupBookingService = groupBookingService;
        this.alarmService = alarmService;
        this.scoreAccountService = scoreAccountService;
        this.accountService = accountService;
        this.messageService = messageService;
        this.itemOrderService = itemOrderService;
    }

    /**
     * 1.
     *
     * @param context   支付成功或失败上下文
     * @param orderList 订单列表
     */
    @Override
    protected void doProcess(PayNotifyContext context, List<Order> orderList) {
        List<String> orderNoList = orderList.stream().map(Order::getOrderNo).collect(Collectors.toList());
        itemService.updateSaleNum(orderNoList);
        for (Order order : orderList) {
            this.updateScore(order);
            order.setPayTime(context.getSuccessTime());
            order.setPayType(PayType.valueOf(context.getTradeType().name()));
            order.setState(order.getDeliveryType() == DeliveryType.EXPRESS ? OrderState.WAIT_DELIVERY : OrderState.WAIT_TAKE);
            order.setVerifyNo(order.getDeliveryType() == DeliveryType.EXPRESS ? null : ProductType.ITEM.generateVerifyNo());
            orderService.updateById(order);
            // 更新item_order状态
            itemOrderService.paySuccess(context.getTradeNo());
            // 更新增加冻结记录
            accountService.paySuccessAddFreeze(order);
            order.setPayTime(context.getSuccessTime());
            order.setPayType(PayType.valueOf(context.getTradeType().name()));
            order.setVerifyNo(order.getDeliveryType() == DeliveryType.EXPRESS ? null : ProductType.ITEM.generateVerifyNo());
            if (order.getBookingNo() == null) {
                order.setState(order.getDeliveryType() == DeliveryType.EXPRESS ? OrderState.WAIT_DELIVERY : OrderState.WAIT_TAKE);
            } else {
                order.setState(OrderState.WAITING_GROUP);
            }
            orderService.updateById(order);
            this.orderAfter(order);
            this.sendRankingNotify(order);
        }
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.PAY_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }

    /**
     * 订单后续处理
     *
     * @param order 订单信息
     */
    private void orderAfter(Order order) {
        if (order.getBookingNo() != null) {
            log.info("该订单为拼团订单,更新拼团订单状态 [{}]", order.getOrderNo());
            GroupBooking booking = groupBookingService.getById(order.getBookingId());
            if (booking == null) {
                alarmService.sendMsg(String.format("支付成功更新拼团时, 未查询到拼团活动 [%s] [%s]", order.getBookingNo(), order.getBookingId()));
                return;
            }
            List<ItemGroupOrder> groupList = itemGroupOrderService.getGroupList(order.getBookingNo(), BookingState.WAITING);
            if (groupList.size() >= booking.getNum()) {
                itemGroupOrderService.updateState(order.getBookingNo(), BookingState.SUCCESS);
                orderService.updateBookingSuccess(order.getBookingNo());
                List<Order> orderList = orderService.getByBookingNo(order.getBookingNo());
                orderList.forEach(this::sendDeliveryNotify);
            } else {
                log.info("拼团订单尚未满员 [{}] [{}] [{}]", order.getBookingNo(), booking.getNum(), groupList.size());
            }
        } else {
            this.sendDeliveryNotify(order);
        }
    }

    /**
     * 发送销售排行消息
     *
     * @param order order
     */
    private void sendRankingNotify(Order order) {
        // 发送消息计算销量或销售金额排行
        List<ItemOrder> itemOrders = itemOrderService.getByOrderNo(order.getOrderNo());
        for (ItemOrder itemOrder : itemOrders) {
            OrderPayNotify notify = new OrderPayNotify();
            notify.setAmount(itemOrder.getSalePrice() * itemOrder.getNum() + itemOrder.getExpressFee());
            notify.setOrderNo(itemOrder.getOrderNo());
            notify.setProductId(itemOrder.getItemId());
            notify.setProductType(ProductType.ITEM);
            notify.setMerchantId(order.getMerchantId());
            notify.setStoreId(itemOrder.getStoreId());
            messageService.send(ExchangeQueue.ORDER_PAY_SUCCESS, notify);
        }
    }

    /**
     * 更新积分
     *
     * @param order 订单信息
     */
    private void updateScore(Order order) {
        if (order.getScoreAmount() > 0) {
            log.info("该订单使用了积分,开始更新积分 [{}]", order.getOrderNo());
            ScoreAccountDTO dto = new ScoreAccountDTO();
            dto.setTradeNo(order.getOrderNo());
            dto.setAmount(order.getScoreAmount());
            dto.setMerchantId(order.getMerchantId());
            dto.setChargeType(ChargeType.ORDER_PAY);
            scoreAccountService.updateAccount(dto);
        }
    }

    /**
     * 发送消息通知商户发货
     *
     * @param order 订单
     */
    private void sendDeliveryNotify(Order order) {
        // 发送消息通知商户发货
        ItemOrderPayNotify payNotify = new ItemOrderPayNotify();
        payNotify.setOrderNo(order.getOrderNo());
        payNotify.setProductType(ProductType.ITEM);
        payNotify.setStoreId(order.getStoreId());
        payNotify.setMerchantId(order.getMerchantId());
        payNotify.setDeliveryType(order.getDeliveryType());
        messageService.send(ExchangeQueue.ITEM_ORDER_NOTIFY, payNotify);
    }
}
