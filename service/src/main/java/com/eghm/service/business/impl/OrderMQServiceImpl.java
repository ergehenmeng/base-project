package com.eghm.service.business.impl;

import com.eghm.common.enums.RabbitQueue;
import com.eghm.constants.ConfigConstant;
import com.eghm.service.business.OrderMQService;
import com.eghm.service.mq.service.MessageService;
import com.eghm.service.sys.impl.SysConfigApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 订单处理类
 * @author 二哥很猛
 * @date 2022/7/28
 */
@Service("orderMQService")
@AllArgsConstructor
public class OrderMQServiceImpl implements OrderMQService {

    private final MessageService rabbitService;

    private final SysConfigApi sysConfigApi;

    @Override
    public void sendOrderExpireMessage(String orderNo) {
        int expireTime = sysConfigApi.getInt(ConfigConstant.ORDER_EXPIRE_TIME);
        rabbitService.sendDelay(orderNo, RabbitQueue.ORDER_PAY_EXPIRE.getExchange(), expireTime);
    }
}
