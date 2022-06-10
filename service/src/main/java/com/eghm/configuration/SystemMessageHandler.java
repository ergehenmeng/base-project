package com.eghm.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author wyb 2022/6/10 15:20
 */
@Component
@Slf4j
public class SystemMessageHandler {

    @RabbitListener(queues = "queue_order_delay")
    public void handler(String message) throws Exception {
        log.info("接受到 [{}]", message);
        throw new Exception("xxxxx");
    }
}
