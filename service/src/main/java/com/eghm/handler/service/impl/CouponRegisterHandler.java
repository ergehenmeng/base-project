package com.eghm.handler.service.impl;

import com.eghm.handler.HandlerInvoker;
import com.eghm.handler.MessageData;
import com.eghm.handler.service.RegisterHandler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2018/12/20 9:51
 */
@Service("couponRegisterHandler")
@Order(11)
public class CouponRegisterHandler implements RegisterHandler {
    @Override
    public void doHandler(MessageData messageData, HandlerInvoker invoker) {
        System.out.println("第二个调用");
        invoker.doHandler(messageData);
    }
}
