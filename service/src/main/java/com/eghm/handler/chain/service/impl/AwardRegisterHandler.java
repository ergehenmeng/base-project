package com.eghm.handler.chain.service.impl;

import com.eghm.handler.chain.Handler;
import com.eghm.handler.chain.HandlerInvoker;
import com.eghm.handler.chain.annotation.HandlerEnum;
import com.eghm.handler.chain.annotation.HandlerMark;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * 积分奖励
 *
 * @author 二哥很猛
 * @date 2018/12/20 9:49
 */
@Service("awardRegisterHandler")
@Order(10)
@Slf4j
@HandlerMark(HandlerEnum.REGISTER)
public class AwardRegisterHandler implements Handler {

    @Override
    public void doHandler(Object messageData, HandlerInvoker invoker) {
        log.info("注册积分奖励");
        invoker.doHandler(messageData);
    }
}
