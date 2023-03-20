package com.eghm.web.controller;

import com.eghm.common.enums.ExchangeQueue;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.mq.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2023/3/15
 */
@AllArgsConstructor
@RestController
@Slf4j
@Api(tags = "消息队列")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/sendDelayMsg")
    @ApiOperation("发送延迟消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msg",  value= "消息", required = true),
            @ApiImplicitParam(name = "delay", value = "延迟多长时间", required = true),
    })
    public RespBody<Void> sendDelayMsg(@RequestParam("msg") String msg, @RequestParam(value = "delay", defaultValue = "10") Integer delay) {
        messageService.sendDelay(ExchangeQueue.ITEM_PAY_EXPIRE, msg, delay);
        return RespBody.success();
    }

    @GetMapping("/sendMsg")
    @ApiOperation("发送消息")
    @ApiImplicitParam(name = "msg",  value= "消息", required = true)
    public RespBody<Void> sendMsg(@RequestParam("msg") String msg) {
        messageService.send(ExchangeQueue.TEST, msg);
        return RespBody.success();
    }

}
