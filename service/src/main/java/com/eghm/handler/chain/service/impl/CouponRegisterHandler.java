package com.eghm.handler.chain.service.impl;

import com.eghm.handler.chain.annotation.HandlerEnum;
import com.eghm.handler.chain.annotation.HandlerMark;
import com.eghm.handler.chain.Handler;
import com.eghm.handler.chain.HandlerInvoker;
import com.eghm.handler.chain.MessageData;
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
@HandlerMark(HandlerEnum.REGISTER)
public class CouponRegisterHandler implements Handler {

    @Override
    public void doHandler(Object messageData, HandlerInvoker invoker) {
        log.info("注册发放优惠券");
        invoker.doHandler(messageData);
    }
}
