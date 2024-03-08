package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.ScoreType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.CloseType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.RefundState;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundNotifyHandler;
import com.eghm.service.member.MemberService;
import com.eghm.service.pay.AggregatePayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2022/9/15
 */
@Service("itemOrderRefundNotifyHandler")
@Slf4j
public class ItemOrderRefundNotifyHandler extends AbstractOrderRefundNotifyHandler {

    private final MemberService memberService;

    private final ItemSkuService itemSkuService;

    private final ItemOrderService itemOrderService;

    private final OrderRefundLogService orderRefundLogService;

    public ItemOrderRefundNotifyHandler(MemberService memberService, OrderService orderService, OrderRefundLogService orderRefundLogService, AggregatePayService aggregatePayService,
                                        VerifyLogService verifyLogService, ItemSkuService itemSkuService, ItemOrderService itemOrderService,
                                        AccountService accountService) {
        super(orderService, accountService, orderRefundLogService, aggregatePayService, verifyLogService);
        this.memberService = memberService;
        this.itemSkuService = itemSkuService;
        this.itemOrderService = itemOrderService;
        this.orderRefundLogService = orderRefundLogService;
    }

    @Override
    protected void refundSuccessSetState(Order order, OrderRefundLog refundLog) {
        int successNum = orderRefundLogService.getRefundSuccessNum(order.getOrderNo(), refundLog.getItemOrderId());
        int productNum = itemOrderService.getProductNum(order.getOrderNo());
        if (successNum + refundLog.getNum() >= productNum) {
            order.setState(OrderState.CLOSE);
            order.setCloseTime(LocalDateTime.now());
            order.setCloseType(CloseType.REFUND);
        }
        order.setRefundState(RefundState.SUCCESS);

        ItemOrder itemOrder = itemOrderService.selectById(refundLog.getItemOrderId());
        // 退款完成库存增加
        itemSkuService.updateStock(itemOrder.getSkuId(), refundLog.getNum());
        memberService.updateScore(itemOrder.getMemberId(), ScoreType.REFUND, order.getScoreAmount());
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.REFUND_SUCCESS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }
}
