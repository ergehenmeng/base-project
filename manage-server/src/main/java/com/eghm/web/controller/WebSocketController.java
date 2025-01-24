package com.eghm.web.controller;

import com.eghm.annotation.SkipPerm;
import com.eghm.dto.ext.RespBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.eghm.constants.CommonConstant.WEBSOCKET_PREFIX;

/**
 * @author 二哥很猛
 * @since 2024/9/11
 */
@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "websocket消息订阅")
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @SubscribeMapping("/subscribe/{objectId}")
    public void subscribe(@DestinationVariable("objectId") Long objectId) {
        log.info("[{}]订阅消息成功", objectId);
    }

    @PostMapping("/broadcast/{objectId}")
    @SkipPerm
    @Operation(summary = "广播消息测试")
    public RespBody<Void> broadcast(@PathVariable("objectId") Long objectId, @RequestBody Map<String, Object> param) {
        simpMessagingTemplate.convertAndSend(WEBSOCKET_PREFIX + "/broadcast/" + objectId, param);
        return RespBody.success();
    }
}
