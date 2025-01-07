package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.order.evaluation.OrderEvaluationDTO;
import com.eghm.dto.business.order.evaluation.OrderEvaluationQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.OrderEvaluationService;
import com.eghm.vo.business.evaluation.EvaluationGroupVO;
import com.eghm.vo.business.evaluation.OrderEvaluationVO;
import com.eghm.vo.business.order.OrderCreateVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@AccessToken
@RestController
@Tag(name="订单评价")
@AllArgsConstructor
@RequestMapping(value = "/webapp/order/evaluation", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderEvaluationController {

    private final OrderEvaluationService orderEvaluationService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "订单评论")
    public RespBody<OrderCreateVO<String>> create(@RequestBody @Validated OrderEvaluationDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        orderEvaluationService.create(dto);
        return RespBody.success();
    }

    @GetMapping("/count")
    @Operation(summary = "统计商品评论数")
    public RespBody<EvaluationGroupVO> count(@Validated IdDTO dto) {
        EvaluationGroupVO vo = orderEvaluationService.groupEvaluation(dto.getId());
        return RespBody.success(vo);
    }

    @GetMapping("/listPage")
    @Operation(summary = "商品评论列表")
    public RespBody<List<OrderEvaluationVO>> listPage(@Validated OrderEvaluationQueryDTO dto) {
        List<OrderEvaluationVO> byPage = orderEvaluationService.getByPage(dto);
        return RespBody.success(byPage);
    }

}
