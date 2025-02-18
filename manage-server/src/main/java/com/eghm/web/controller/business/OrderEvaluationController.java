package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.order.evaluation.OrderEvaluationQueryRequest;
import com.eghm.dto.business.order.evaluation.OrderEvaluationShieldDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.OrderEvaluationService;
import com.eghm.vo.business.evaluation.OrderEvaluationResponse;
import com.eghm.vo.business.order.OrderCreateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@RestController
@Tag(name="订单评价")
@AllArgsConstructor
@RequestMapping(value = "/manage/order/evaluation", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderEvaluationController {

    private final OrderEvaluationService orderEvaluationService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<OrderEvaluationResponse>> listPage(@ParameterObject @Validated OrderEvaluationQueryRequest dto) {
        Page<OrderEvaluationResponse> byPage = orderEvaluationService.listPage(dto);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/shield", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "屏蔽")
    public RespBody<OrderCreateVO<String>> audit(@RequestBody @Validated OrderEvaluationShieldDTO dto) {
        dto.setUserId(SecurityHolder.getUserId());
        orderEvaluationService.shield(dto);
        return RespBody.success();
    }

}
