package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eghm.dto.business.order.adjust.OrderAdjustRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.OrderState;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.OrderAdjustLogMapper;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderAdjustLog;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.OrderAdjustLogService;
import com.eghm.service.business.OrderService;
import com.eghm.vo.business.order.adjust.OrderAdjustResponse;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单改价记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-25
 */
@Slf4j
@AllArgsConstructor
@Service("orderAdjustLogService")
public class OrderAdjustLogServiceImpl extends ServiceImpl<OrderAdjustLogMapper, OrderAdjustLog> implements OrderAdjustLogService {

    private final OrderService orderService;

    private final ItemOrderService itemOrderService;

    @Override
    public List<OrderAdjustResponse> getList(String orderNo) {
        return baseMapper.getList(orderNo);
    }

    @Override
    public void itemAdjust(OrderAdjustRequest request) {
        Order order = orderService.getByOrderNo(request.getOrderNo());
        if (order.getState() != OrderState.UN_PAY) {
            log.error("订单不支持改价,订单号:[{}]", request.getOrderNo());
            throw new BusinessException(ErrorCode.ORDER_NOT_ADJUST);
        }
        List<ItemOrder> orderList = itemOrderService.getByOrderNo(request.getOrderNo());
        List<OrderAdjustLog> adjustList = Lists.newArrayListWithExpectedSize(8);
        for (ItemOrder itemOrder : orderList) {
            if (itemOrder.getId().equals(request.getId())) {
                OrderAdjustLog adjustLog = new OrderAdjustLog();
                adjustLog.setOrderNo(request.getOrderNo());
                adjustLog.setUserId(request.getUserId());
                adjustLog.setUserName(request.getUserName());
                adjustLog.setSourcePrice(itemOrder.getSalePrice());
                adjustLog.setTargetPrice(request.getPrice());
                adjustLog.setProductName(itemOrder.getTitle() + " " + itemOrder.getSkuTitle());
                itemOrder.setSalePrice(request.getPrice());
                adjustList.add(adjustLog);
            }
        }
        int totalPrice = orderList.stream().mapToInt(value -> value.getNum() * value.getSalePrice()).sum();
        int payAmount = totalPrice + order.getExpressAmount() - order.getCdKeyAmount() - order.getDiscountAmount() - order.getScoreAmount();
        if (payAmount <= 0) {
            throw new BusinessException(ErrorCode.ORDER_LE_ZERO);
        }
        order.setPayAmount(payAmount);
        itemOrderService.updateBatchById(orderList);
        OrderAdjustLogService service = (OrderAdjustLogService) AopContext.currentProxy();
        service.saveBatch(adjustList);
        orderService.updateById(order);
    }
}
