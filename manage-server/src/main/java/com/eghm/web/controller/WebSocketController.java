package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2024/9/11
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "websocket消息订阅")
public class WebSocketController {

    @SubscribeMapping("/order/broadcast/{token}")
    public RespBody<Void> subscribe(@DestinationVariable("token") String token, SimpMessageHeaderAccessor accessor) {
        log.info("客户端 [{}] 订阅消息成功 [{}]", token, accessor);
        return RespBody.success();
    }
}
