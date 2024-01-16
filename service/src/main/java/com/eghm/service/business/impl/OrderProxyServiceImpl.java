package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.order.homestay.HomestayOrderConfirmRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.SmsType;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.enums.ref.ConfirmState;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.HomestayOrderMapper;
import com.eghm.model.HomestayOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderVisitor;
import com.eghm.service.business.OrderProxyService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.context.RefundApplyContext;
import com.eghm.service.common.SmsService;
import com.eghm.state.machine.StateHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 该类为了解决构造方法注入循环依赖
 *
 * @author 二哥很猛
 * @since 2024/1/11
 */
@Slf4j
@Service("orderProxyService")
@AllArgsConstructor
public class OrderProxyServiceImpl implements OrderProxyService {

    private final HomestayOrderMapper homestayOrderMapper;

    private final OrderService orderService;

    private final OrderVisitorService orderVisitorService;

    private final StateHandler stateHandler;

    private final SmsService smsService;

    private HomestayOrder getByOrderNo(String orderNo) {
        LambdaQueryWrapper<HomestayOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(HomestayOrder::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return homestayOrderMapper.selectOne(wrapper);
    }

    @Override
    public void confirm(HomestayOrderConfirmRequest request) {
        HomestayOrder homestayOrder = this.getByOrderNo(request.getOrderNo());
        if (homestayOrder.getConfirmState() != ConfirmState.WAIT_CONFIRM) {
            log.warn("该民宿订单已确认 [{}] [{}]", request.getOrderNo(), homestayOrder.getConfirmState());
            throw new BusinessException(ErrorCode.ORDER_CONFIRM);
        }
        homestayOrder.setConfirmState(request.getConfirmState());
        homestayOrder.setRemark(request.getRemark());
        homestayOrderMapper.updateById(homestayOrder);

        if (request.getConfirmState() == ConfirmState.FAIL_CONFIRM) {
            log.info("订单:[{}],确认无房开始执行退款逻辑", request.getOrderNo());
            Order order = orderService.getByOrderNo(request.getOrderNo());
            List<OrderVisitor> visitorList = orderVisitorService.getByOrderNo(request.getOrderNo());
            List<Long> visitorIds = visitorList.stream().map(OrderVisitor::getId).collect(Collectors.toList());
            RefundApplyContext context = new RefundApplyContext();
            context.setMemberId(order.getMemberId());
            context.setNum(visitorIds.size());
            context.setVisitorIds(visitorIds);
            context.setApplyType(1);
            context.setReason(request.getRemark());
            context.setApplyAmount(order.getPayAmount());
            context.setOrderNo(request.getOrderNo());
            stateHandler.fireEvent(ProductType.HOMESTAY, order.getState().getValue(), HomestayEvent.CONFIRM_ROOM, context);
            // 发送短信通知
            smsService.sendSms(SmsType.CONFIRM_NO_ROOM, order.getMobile(), request.getOrderNo());
        }
    }
}
