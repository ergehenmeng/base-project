package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.evaluation.OrderEvaluationDTO;
import com.eghm.dto.business.order.evaluation.OrderEvaluationQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.OrderEvaluationService;
import com.eghm.vo.business.evaluation.OrderEvaluationVO;
import com.eghm.vo.business.order.OrderCreateVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@AccessToken
@RestController
@Api(tags = "订单评价")
@AllArgsConstructor
@RequestMapping("/webapp/order/evaluation")
public class OrderEvaluationController {

    private final OrderEvaluationService orderEvaluationService;

    @PostMapping("/evaluate")
    @ApiOperation("订单评论")
    public RespBody<OrderCreateVO<String>> evaluate(@RequestBody @Validated OrderEvaluationDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        orderEvaluationService.evaluate(dto);
        return RespBody.success();
    }

    @GetMapping("/listPage")
    @ApiOperation("商品评论列表")
    public RespBody<PageData<OrderEvaluationVO>> listPage(@Validated OrderEvaluationQueryDTO dto) {
        Page<OrderEvaluationVO> byPage = orderEvaluationService.getByPage(dto);
        return RespBody.success(PageData.toPage(byPage));
    }
}
