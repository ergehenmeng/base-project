package com.eghm.service.business.handler.state.impl.line;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.context.RefundApplyContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundApplyHandler;
import com.eghm.utils.DecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.eghm.enums.ErrorCode.REFUND_AMOUNT_MAX;

/**
 * @author 殿小二
 * @date 2022/9/3
 */
@Service("lineApplyRefundHandler")
@Slf4j
public class LineOrderRefundApplyHandler extends AbstractOrderRefundApplyHandler {
    
    public LineOrderRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService,
                                       OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }

    @Override
    protected void before(RefundApplyContext context, Order order) {
        super.before(context, order);
        int totalAmount = order.getPrice() * context.getNum();
        if (totalAmount < context.getApplyAmount()) {
            throw new BusinessException(REFUND_AMOUNT_MAX.getCode(), String.format(REFUND_AMOUNT_MAX.getMsg(), DecimalUtil.centToYuan(totalAmount)));
        }
    }

    @Override
    public IEvent getEvent() {
        return LineEvent.REFUND_APPLY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.LINE;
    }
}
