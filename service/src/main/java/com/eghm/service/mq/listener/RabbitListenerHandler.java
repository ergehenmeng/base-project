package com.eghm.service.mq.listener;

import com.eghm.common.constant.QueueConstant;
import com.eghm.service.business.CommonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 二哥很猛
 * @date 2022/7/28
 */
@Component
@AllArgsConstructor
@Slf4j
public class RabbitListenerHandler {

    private final CommonService commonService;

    /**
     * 消息队列订单过期处理
     * @param orderNo 订单编号
     */
    @RabbitListener(queues = QueueConstant.ORDER_PAY_EXPIRE_QUEUE)
    public void orderExpire(String orderNo) {
        commonService.getOrderService(orderNo).orderExpire(orderNo);
    }

}
