package com.eghm.app.manage.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * @author 二哥很猛
 * @since 2024/9/11
 */
@Slf4j
@Controller
@AllArgsConstructor
@Tag(name = "websocket消息订阅")
public class WebSocketController {

    @SubscribeMapping("/subscribe/{objectId}")
    public void subscribe(@DestinationVariable("objectId") Long objectId) {
        log.info("[{}]订阅消息成功", objectId);
    }

}
