package com.eghm.service.business.handler.state.impl.homestay;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.service.business.handler.context.ApplyRefundContext;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.state.impl.AbstractRefundApplyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/23
 */
@Service("homestayApplyRefundHandler")
@Slf4j
public class HomestayRefundApplyHandler extends AbstractRefundApplyHandler {

    public HomestayRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }

    @Override
    protected void before(ApplyRefundContext dto, Order order) {
        super.before(dto, order);
        if (dto.getNum() != dto.getVisitorIds().size()) {
            log.error("退款数量和退款人数不一致 [{}] [{}] [{}]", dto.getOrderNo(), dto.getNum(), dto.getVisitorIds().size());
            throw new BusinessException(ErrorCode.REFUND_VISITOR);
        }
    }

    @Override
    public IEvent getEvent() {
        return null;
    }

    @Override
    public ProductType getStateMachineType() {
        return null;
    }
}
