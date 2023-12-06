package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.service.business.ItemService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.context.PayNotifyContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 普通商品订单支付通知
 * 说明:
 * 1. 会存在多个商品关联一个支付流水号
 * 2. 该接口是
 * @author 二哥很猛
 * @date 2022/9/9
 */
@Service("itemPaySuccessHandler")
@Slf4j
public class ItemOrderPaySuccessHandler extends AbstractOrderPayNotifyHandler {

    private final ItemService itemService;

    private final OrderService orderService;

    public ItemOrderPaySuccessHandler(OrderService orderService, ItemService itemService) {
        super(orderService);
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @Override
    protected void doProcess(PayNotifyContext context, List<String> orderNoList) {
        orderService.updateState(orderNoList, OrderState.UN_USED, OrderState.UN_PAY, OrderState.PROGRESS);
        itemService.updateSaleNum(orderNoList);
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.PAY_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }

}
