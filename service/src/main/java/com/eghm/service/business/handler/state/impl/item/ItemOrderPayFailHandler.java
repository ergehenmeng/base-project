package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.ProductType;
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
 *
 * @author 二哥很猛
 * @since 2022/9/9
 */
@Service("itemPayFailHandler")
@Slf4j
public class ItemOrderPayFailHandler extends AbstractOrderPayNotifyHandler {

    public ItemOrderPayFailHandler(OrderService orderService) {
        super(orderService);
    }

    @Override
    protected void doProcess(PayNotifyContext context, List<String> orderNoList) {
        log.error("零售异步支付失败 [{}]", context.getOrderNo());
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.PAY_FAIL;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }

}
