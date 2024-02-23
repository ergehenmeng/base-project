package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.order.evaluation.OrderEvaluationAuditDTO;
import com.eghm.dto.business.order.evaluation.OrderEvaluationQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.OrderEvaluationService;
import com.eghm.vo.business.evaluation.OrderEvaluationResponse;
import com.eghm.vo.business.order.OrderCreateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@RestController
@Api(tags = "订单评价")
@AllArgsConstructor
@RequestMapping(value = "/manage/order/evaluation", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderEvaluationController {

    private final OrderEvaluationService orderEvaluationService;

    @GetMapping("/listPage")
    @ApiOperation("评论列表")
    public RespBody<PageData<OrderEvaluationResponse>> listPage(@Validated OrderEvaluationQueryRequest dto) {
        Page<OrderEvaluationResponse> byPage = orderEvaluationService.listPage(dto);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/audit", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("订单评论审核")
    public RespBody<OrderCreateVO<String>> audit(@RequestBody @Validated OrderEvaluationAuditDTO dto) {
        dto.setUserId(SecurityHolder.getUserId());
        orderEvaluationService.audit(dto);
        return RespBody.success();
    }

}
