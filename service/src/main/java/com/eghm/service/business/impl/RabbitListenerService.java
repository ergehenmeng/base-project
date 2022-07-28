package com.eghm.service.business.impl;

import com.eghm.common.constant.QueueConstant;
import com.eghm.service.business.TicketOrderService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 二哥很猛
 * @date 2022/7/28
 */
@Component
@AllArgsConstructor
public class RabbitListenerService {

    @Autowired
    private final TicketOrderService ticketOrderService;

    @RabbitListener(queues = QueueConstant.ORDER_PAY_EXPIRE_QUEUE)
    public void orderExpire(String orderNo) {

    }

}
