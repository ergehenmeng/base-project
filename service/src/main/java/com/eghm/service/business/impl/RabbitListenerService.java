package com.eghm.service.business.impl;

import com.eghm.common.constant.QueueConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import com.eghm.service.business.OrderService;
import com.eghm.utils.SpringContextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @date 2022/7/28
 */
@Component
@AllArgsConstructor
@Slf4j
public class RabbitListenerService {


    /**
     * 消息队列订单过期处理
     * @param orderNo 订单编号
     */
    @RabbitListener(queues = QueueConstant.ORDER_PAY_EXPIRE_QUEUE)
    public void orderExpire(String orderNo) {
        this.getOrderService(orderNo).orderExpire(orderNo);
    }


    /**
     * 获取符合该订单处理实现的bean
     * @param orderNo 订单编号
     * @return bean
     */
    private OrderService getOrderService(String orderNo) {
        String orderType = Arrays.stream(ProductType.values())
                .filter(productType -> orderNo.startsWith(productType.getPrefix()))
                .map(ProductType::getValue)
                .findFirst()
                .orElseThrow(() -> {
                    log.error("该订单类型不匹配 [{}]", orderNo);
                    return new BusinessException(ErrorCode.ORDER_TYPE_MATCH);
                });
        String beanName = orderType + OrderService.class.getSimpleName();
        return SpringContextUtil.getBean(beanName, OrderService.class);
    }

}
