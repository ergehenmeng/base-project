package com.eghm.handler.service.impl;

import com.eghm.handler.HandlerInvoker;
import com.eghm.handler.MessageData;
import com.eghm.handler.service.RegisterHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2018/12/20 9:49
 */
@Service("awardRegisterHandler")
@Order(13)
@Slf4j
public class AwardRegisterHandler implements RegisterHandler {

    @Override
    public void doHandler(MessageData messageData, HandlerInvoker invoker) {
        log.info("第一个调用链");
        invoker.doHandler(messageData);
    }
}
