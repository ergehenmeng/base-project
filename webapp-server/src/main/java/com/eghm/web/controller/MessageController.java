package com.eghm.web.controller;

import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.OrderPayNotify;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ExchangeQueue;
import com.eghm.mq.service.MessageService;
import com.eghm.web.annotation.SignCheck;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/3/15
 */
@AllArgsConstructor
@RestController
@Slf4j
@Tag(name="消息队列")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/sendDelayMsg")
    @Operation(summary = "发送延迟消息")
    @Parameter(name = "msg", description = "消息", required = true)
    @Parameter(name = "delay", description = "延迟多长时间", required = true)
    public RespBody<Void> sendDelayMsg(@RequestParam("msg") String msg, @RequestParam(value = "delay", defaultValue = "10") Integer delay) {
        messageService.sendDelay(ExchangeQueue.ITEM_PAY_EXPIRE, msg, delay);
        return RespBody.success();
    }

    @PostMapping("/signCheck")
    @Operation(summary = "签名信息校验")
    @SignCheck
    public RespBody<IdDTO> signCheck(@RequestBody IdDTO dto) {
        return RespBody.success(dto);
    }

    @PostMapping("/sendBorder")
    @Operation(summary = "发送广播消息")
    public RespBody<Void> sendMsg(@RequestBody OrderPayNotify notify) {
        messageService.send(ExchangeQueue.ORDER_PAY_SUCCESS, notify);
        return RespBody.success();
    }
}
