package com.eghm.state.machine.impl.item;

import com.eghm.enums.ScoreType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.OrderState;
import com.eghm.enums.ProductType;
import com.eghm.model.Order;
import com.eghm.service.business.OrderService;
import com.eghm.service.member.MemberService;
import com.eghm.state.machine.context.PayNotifyContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 普通商品订单支付通知
 * 说明:
 * 1. 会存在多个商品关联一个支付流水号
 * 2. 待完成
 *
 * @author 二哥很猛
 * @since 2022/9/9
 */
@Service("itemOrderPayFailHandler")
@Slf4j
public class ItemOrderPayFailHandler extends AbstractItemOrderPayNotifyHandler {

    private final MemberService memberService;

    public ItemOrderPayFailHandler(OrderService orderService, MemberService memberService) {
        super(orderService);
        this.memberService = memberService;
    }

    @Override
    protected void doProcess(PayNotifyContext context, List<Order> orderList) {
        log.error("零售异步支付失败 [{}]", context.getOrderNo());
    }

    @Override
    protected void after(PayNotifyContext context, List<Order> orderList) {
        for (Order order : orderList) {
            boolean flag = (order.getState() == OrderState.UN_PAY || order.getState() == OrderState.PROGRESS) && order.getScoreAmount() > 0;
            if (flag) {
                memberService.updateScore(order.getMemberId(), ScoreType.PAY_CANCEL, order.getScoreAmount());
            }
        }
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
