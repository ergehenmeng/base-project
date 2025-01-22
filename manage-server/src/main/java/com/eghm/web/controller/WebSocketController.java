package com.eghm.web.controller;

import com.eghm.annotation.SkipPerm;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.ext.SocketMsg;
import com.eghm.enums.ProductType;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.eghm.constants.CommonConstant.WEBSOCKET_PREFIX;

/**
 * @author 二哥很猛
 * @since 2024/9/11
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "websocket消息订阅")
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @SubscribeMapping("/order/broadcast/{merchantId}")
    public void subscribe(@DestinationVariable("merchantId") Long merchantId) {
        log.info("商户[{}]订阅消息成功", merchantId);
    }

    @GetMapping("/order/broadcast")
    @SkipPerm
    @ApiOperation("广播消息测试")
    public RespBody<Void> broadcast(@RequestParam("merchantId") Long merchantId) {
        simpMessagingTemplate.convertAndSend(WEBSOCKET_PREFIX + "/order/broadcast/" + merchantId, SocketMsg.delivery(Lists.newArrayList(ProductType.ITEM.generateOrderNo())));
        return RespBody.success();
    }
}
