package com.eghm.web.controller.business;

import com.eghm.dto.business.order.OrderDTO;
import com.eghm.dto.business.order.adjust.OrderAdjustRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.OrderAdjustLogService;
import com.eghm.vo.business.order.adjust.OrderAdjustResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "列表")
    @GetMapping("/list")
    public RespBody<List<OrderAdjustResponse>> getList(@ParameterObject @Validated OrderDTO dto) {
        List<OrderAdjustResponse> responseList = orderAdjustLogService.getList(dto.getOrderNo());
        return RespBody.success(responseList);
    }

    @Operation(summary = "零售改价")
    @PostMapping(value = "/item", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> item(@RequestBody @Validated OrderAdjustRequest request) {
        orderAdjustLogService.itemAdjust(request);
        return RespBody.success();
    }

}
