package com.eghm.web.controller.business;

import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.order.adjust.OrderAdjustRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.ext.UserToken;
import com.eghm.service.business.OrderAdjustLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2024/3/25
 */

@RestController
@Tag(name="订单改价")
@AllArgsConstructor
@RequestMapping(value = "/manage/order/adjust", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderAdjustController {

    private final OrderAdjustLogService orderAdjustLogService;

    @Operation(summary = "零售改价")
    @PostMapping(value = "/item", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> item(@RequestBody @Validated OrderAdjustRequest request) {
        UserToken user = SecurityHolder.getUser();
        request.setUserId(user.getId());
        request.setUserName(user.getNickName());
        orderAdjustLogService.itemAdjust(request);
        return RespBody.success();
    }

}
