package com.eghm.handler.chain.service.impl;

import com.eghm.handler.chain.Handler;
import com.eghm.handler.chain.HandlerInvoker;
import com.eghm.handler.chain.MessageData;
import com.eghm.handler.chain.service.RegisterHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * 注册送优惠券
 * @author 二哥很猛
 * @date 2018/12/20 9:51
 */
@Service("couponRegisterHandler")
@Order(20)
@Slf4j
public class CouponRegisterHandler implements RegisterHandler {
    @Override
    public void doHandler(MessageData messageData, HandlerInvoker<? extends Handler> invoker) {
        log.info("注册发放优惠券");
        invoker.doHandler(messageData);
    }
}
