package com.eghm.state.machine.impl.item;

import com.eghm.common.AlarmService;
import com.eghm.dto.business.account.score.ScoreAccountDTO;
import com.eghm.dto.ext.OrderPayNotify;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ChargeType;
import com.eghm.enums.DeliveryType;
import com.eghm.enums.OrderState;
import com.eghm.enums.ProductType;
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

    private final ScoreAccountService scoreAccountService;

    private final OrderService orderService;

    private final ItemGroupOrderService itemGroupOrderService;

    private final GroupBookingService groupBookingService;

    private final AlarmService alarmService;

    private final AccountService accountService;

    private final ItemOrderService itemOrderService;

    private final MessageService messageService;

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
        List<String> orderNoList = orderList.stream().map(Order::getOrderNo).toList();
        itemService.updateSaleNum(orderNoList);
        for (Order order : orderList) {
            if (order.getBookingNo() != null) {
                log.info("该订单为拼团订单,更新拼团订单状态 [{}]", order.getOrderNo());
                this.tryUpdateGroupOrderState(order.getBookingNo(), order.getBookingId());
            }
            if (order.getScoreAmount() > 0) {
                log.info("该订单使用了积分,开始更新积分 [{}]", order.getOrderNo());
                ScoreAccountDTO dto = new ScoreAccountDTO();
                dto.setTradeNo(order.getOrderNo());
                dto.setAmount(order.getScoreAmount());
                dto.setMerchantId(order.getMerchantId());
                dto.setChargeType(ChargeType.ORDER_PAY);
                scoreAccountService.updateAccount(dto);
            }
            List<ItemOrder> itemOrderList = itemOrderService.getByOrderNo(order.getOrderNo());
            boolean anyMatch = itemOrderList.stream().anyMatch(itemOrder -> itemOrder.getDeliveryType() == DeliveryType.EXPRESS);
            order.setPayTime(context.getSuccessTime());
            order.setState(anyMatch ? OrderState.WAIT_DELIVERY : OrderState.WAIT_TAKE);
            orderService.updateById(order);
            // 更新item_order状态
            itemOrderService.paySuccess(context.getOrderNo());
            // 更新增加冻结记录
            accountService.paySuccessAddFreeze(order);
            // 发送消息
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
     * 更新拼团订单状态
     *
     * @param bookingNo 拼团单号
     * @param bookingId 拼团活动id
     */
    private void tryUpdateGroupOrderState(String bookingNo, Long bookingId) {
        GroupBooking booking = groupBookingService.getById(bookingId);
        if (booking == null) {
            alarmService.sendMsg(String.format("支付成功更新拼团时, 未查询到拼团活动 [%s] [%s]", bookingNo, bookingId));
            return;
        }
        List<ItemGroupOrder> groupList = itemGroupOrderService.getGroupList(bookingNo, 0);
        if (groupList.size() >= booking.getNum()) {
            itemGroupOrderService.updateState(bookingNo, 1);
            orderService.updateBookingState(bookingNo, 1);
        } else {
            log.info("拼团订单尚未满员 [{}] [{}] [{}]", bookingNo, booking.getNum(), groupList.size());
        }
    }
}
