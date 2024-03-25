package com.eghm.service.business.handler.state.impl.item;

import com.eghm.dto.business.account.score.ScoreAccountDTO;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.ChargeType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.GroupBooking;
import com.eghm.model.ItemGroupOrder;
import com.eghm.model.Order;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.sys.AlarmService;
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

    public ItemOrderPaySuccessHandler(ScoreAccountService scoreAccountService, OrderService orderService, ItemService itemService, ItemGroupOrderService itemGroupOrderService,
                                      GroupBookingService groupBookingService, AlarmService alarmService, AccountService accountService) {
        super(orderService);
        this.itemService = itemService;
        this.orderService = orderService;
        this.itemGroupOrderService = itemGroupOrderService;
        this.groupBookingService = groupBookingService;
        this.alarmService = alarmService;
        this.scoreAccountService = scoreAccountService;
        this.accountService = accountService;
    }

    @Override
    protected void doProcess(PayNotifyContext context, List<String> orderNoList) {
        orderService.updateState(orderNoList, context.getSuccessTime(), OrderState.UN_USED, OrderState.UN_PAY, OrderState.PROGRESS);
        itemService.updateSaleNum(orderNoList);
    }

    @Override
    protected void after(PayNotifyContext context, List<Order> orderList) {
        for (Order order : orderList) {
            if (order.getBookingNo() != null) {
                log.info("该订单为拼团订单,更新拼团订单状态 [{}]", order.getOrderNo());
                this.tryUpdateGroupOrderState(order.getBookingNo(), order.getBookingId());
            }
            if (order.getScoreAmount() > 0) {
                ScoreAccountDTO dto = new ScoreAccountDTO();
                dto.setTradeNo(order.getOrderNo());
                dto.setAmount(order.getScoreAmount());
                dto.setMerchantId(order.getMerchantId());
                dto.setChargeType(ChargeType.ORDER_PAY);
                scoreAccountService.updateAccount(dto);
            }
            accountService.paySuccessAddFreeze(order);
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
