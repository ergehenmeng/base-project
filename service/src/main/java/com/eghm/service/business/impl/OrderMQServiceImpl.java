package com.eghm.service.business.impl;

import cn.hutool.core.util.IdUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.enums.ExchangeQueue;
import com.eghm.service.business.OrderMQService;
import com.eghm.service.mq.service.MessageService;
import com.eghm.service.sys.impl.SysConfigApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 订单处理类
 *
 * @author 二哥很猛
 * @date 2022/7/28
 */
@Service("orderMQService")
@AllArgsConstructor
public class OrderMQServiceImpl implements OrderMQService {

    private final MessageService rabbitService;

    private final SysConfigApi sysConfigApi;

    @Override
    public void sendOrderExpireMessage(ExchangeQueue exchangeQueue, String orderNo) {
        int expireTime = sysConfigApi.getInt(ConfigConstant.ORDER_EXPIRE_TIME);
        rabbitService.sendDelay(exchangeQueue, orderNo, expireTime);
    }

    @Override
    public void sendOrderCreateMessage(ExchangeQueue exchangeQueue, AsyncKey context) {
        context.setKey(IdUtil.fastSimpleUUID());
        rabbitService.sendAsync(exchangeQueue, context);
    }

    @Override
    public void sendOrderCompleteMessage(ExchangeQueue exchangeQueue, String orderNo) {
        int completeTime = sysConfigApi.getInt(ConfigConstant.ORDER_COMPLETE_TIME);
        rabbitService.sendDelay(exchangeQueue, orderNo, completeTime);
    }
}
