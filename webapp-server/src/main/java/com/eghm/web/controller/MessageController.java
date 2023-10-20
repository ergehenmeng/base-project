package com.eghm.web.controller;

import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ExchangeQueue;
import com.eghm.service.mq.service.MessageService;
import com.eghm.web.annotation.SignCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("/signCheck")
    @ApiOperation("签名信息校验")
    @SignCheck
    public RespBody<IdDTO> signCheck(@RequestBody IdDTO dto) {
        return RespBody.success(dto);
    }
}
