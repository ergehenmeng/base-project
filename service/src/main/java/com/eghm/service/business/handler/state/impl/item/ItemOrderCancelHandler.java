package com.eghm.service.business.handler.state.impl.item;

import cn.hutool.core.util.StrUtil;
import com.eghm.enums.ScoreType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.pay.AggregatePayService;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.state.impl.AbstractOrderCancelHandler;
import com.eghm.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @since 2022/8/25
 */
@Service("itemOrderCancelHandler")
@Slf4j
public class ItemOrderCancelHandler extends AbstractOrderCancelHandler {

    private final MemberService memberService;

    private final ItemSkuService itemSkuService;

    private final ItemOrderService itemOrderService;

    private final ItemGroupOrderService itemGroupOrderService;

    public ItemOrderCancelHandler(MemberService memberService, OrderService orderService, MemberCouponService memberCouponService, ItemSkuService itemSkuService, ItemOrderService itemOrderService, ItemGroupOrderService itemGroupOrderService, AggregatePayService aggregatePayService) {
        super(orderService, memberCouponService, aggregatePayService);
        this.itemSkuService = itemSkuService;
        this.itemOrderService = itemOrderService;
        this.itemGroupOrderService = itemGroupOrderService;
        this.memberService = memberService;
    }

    @Override
    protected void after(Order order) {
        super.after(order);
        List<ItemOrder> orderList = itemOrderService.getByOrderNo(order.getOrderNo());
        Map<Long, Integer> skuNumMap = orderList.stream().collect(Collectors.toMap(ItemOrder::getSkuId, ItemOrder::getNum));
        itemSkuService.updateStock(skuNumMap);
        if (StrUtil.isNotBlank(order.getBookingNo())) {
            itemGroupOrderService.delete(order.getBookingNo(), order.getOrderNo());
        }
        if (order.getScoreAmount() > 0) {
            memberService.updateScore(order.getMemberId(), ScoreType.PAY_CANCEL, order.getScoreAmount());
        }
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.CANCEL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }
}
