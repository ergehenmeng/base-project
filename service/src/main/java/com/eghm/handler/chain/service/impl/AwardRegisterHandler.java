package com.eghm.handler.chain.service.impl;

import com.eghm.handler.chain.Handler;
import com.eghm.handler.chain.HandlerInvoker;
import com.eghm.handler.chain.MessageData;
import com.eghm.handler.chain.service.RegisterHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * 积分奖励
 * @author 二哥很猛
 * @date 2018/12/20 9:49
 */
@Service("awardRegisterHandler")
@Order(10)
@Slf4j
public class AwardRegisterHandler implements RegisterHandler {

    @Override
    public void doHandler(MessageData messageData, HandlerInvoker<? extends Handler> invoker) {
        log.info("注册积分奖励");
        invoker.doHandler(messageData);
    }
}
