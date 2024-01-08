package com.eghm.service.business.handler.state.impl.homestay;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.HomestayEvent;
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
 * @author 二哥很猛
 * @date 2022/8/23
 */
@Service("homestayApplyRefundHandler")
@Slf4j
public class HomestayOrderRefundApplyHandler extends AbstractOrderRefundApplyHandler {

    public HomestayOrderRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService) {
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
        return HomestayEvent.REFUND_APPLY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.HOMESTAY;
    }
}
