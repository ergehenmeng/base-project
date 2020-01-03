package com.fanyin.handler.service.impl;

import com.fanyin.handler.HandlerInvoker;
import com.fanyin.handler.MessageData;
import com.fanyin.handler.service.RegisterHandler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2018/12/20 9:49
 */
@Service("awardRegisterHandler")
@Order(13)
public class AwardRegisterHandler implements RegisterHandler {

    @Override
    public void doHandler(MessageData messageData, HandlerInvoker invoker) {
        System.out.println("第一个调用链");
        invoker.doHandler(messageData);
    }
}
