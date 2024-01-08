package com.eghm.service.business.handler.state.impl.ticket;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.TicketEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.TicketOrder;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.TicketOrderService;
import com.eghm.service.business.handler.context.RefundApplyContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundApplyHandler;
import com.eghm.utils.DecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.eghm.enums.ErrorCode.REFUND_AMOUNT_MAX;

/**
 * @author 二哥很猛
 * @date 2022/8/19
 */
@Service("ticketApplyRefundHandler")
@Slf4j
public class TicketOrderRefundApplyHandler extends AbstractOrderRefundApplyHandler {

    private final TicketOrderService ticketOrderService;

    private final OrderVisitorService orderVisitorService;

    public TicketOrderRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, TicketOrderService ticketOrderService) {
        super(orderService, orderRefundLogService, orderVisitorService);
        this.ticketOrderService = ticketOrderService;
        this.orderVisitorService = orderVisitorService;
    }

    @Override
    protected void before(RefundApplyContext context, Order order) {
        super.before(context, order);
        TicketOrder ticketOrder = ticketOrderService.getByOrderNo(context.getOrderNo());
        if (Boolean.TRUE.equals(ticketOrder.getRealBuy()) && context.getNum() != context.getVisitorIds().size()) {
            log.error("退款数量和退款人数不一致 [{}] [{}] [{}]", context.getOrderNo(), context.getNum(), context.getVisitorIds().size());
            throw new BusinessException(ErrorCode.REFUND_VISITOR);
        }
        int totalAmount = order.getPrice() * context.getNum();
        if (totalAmount < context.getApplyAmount()) {
            throw new BusinessException(REFUND_AMOUNT_MAX.getCode(), String.format(REFUND_AMOUNT_MAX.getMsg(), DecimalUtil.centToYuan(totalAmount)));
        }
    }

    @Override
    protected int getVerifyNum(Order order) {
        return (int) orderVisitorService.getVerify(order.getOrderNo());
    }

    @Override
    public IEvent getEvent() {
        return TicketEvent.REFUND_APPLY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.TICKET;
    }
}
