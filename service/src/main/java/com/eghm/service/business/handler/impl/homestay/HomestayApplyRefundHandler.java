package com.eghm.service.business.handler.impl.homestay;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.service.business.handler.dto.ApplyRefundDTO;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.impl.DefaultApplyRefundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/23
 */
@Service("homestayApplyRefundHandler")
@Slf4j
public class HomestayApplyRefundHandler extends DefaultApplyRefundHandler {

    public HomestayApplyRefundHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService) {
        super(orderService, orderRefundLogService, orderVisitorService);
    }

    @Override
    protected void before(ApplyRefundDTO dto, Order order) {
        super.before(dto, order);
        if (dto.getNum() != dto.getVisitorIds().size()) {
            log.error("退款数量和退款人数不一致 [{}] [{}] [{}]", dto.getOrderNo(), dto.getNum(), dto.getVisitorIds().size());
            throw new BusinessException(ErrorCode.REFUND_VISITOR);
        }
    }
}
