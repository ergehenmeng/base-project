package com.eghm.web.controller.business;

import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.statistics.CollectRequest;
import com.eghm.dto.statistics.DateRequest;
import com.eghm.dto.statistics.ProductRequest;
import com.eghm.dto.statistics.VisitRequest;
import com.eghm.service.business.*;
import com.eghm.service.member.MemberService;
import com.eghm.vo.business.statistics.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/22
 */

@RestController
@Api(tags = "统计分析")
@AllArgsConstructor
@RequestMapping(value = "/manage/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsController {

    private final MemberService memberService;

    private final OrderService orderService;

    private final MemberVisitLogService memberVisitLogService;

    private final MemberCollectService memberCollectService;

    private final CommonService commonService;

    private final ShoppingCartService shoppingCartService;

    @GetMapping("/register")
    @ApiOperation("注册统计")
    public RespBody<Integer> register(DateRequest request) {
        Integer statistics = memberService.registerStatistics(request);
        return RespBody.success(statistics);
    }

    @GetMapping("/dayRegister")
    @ApiOperation("注册统计(按天)")
    public RespBody<List<MemberRegisterVO>> dayRegister(DateRequest request) {
        this.setNull(request);
        List<MemberRegisterVO> statistics = memberService.dayRegister(request);
        return RespBody.success(statistics);
    }

    @GetMapping("/order")
    @ApiOperation("下单统计")
    public RespBody<OrderStatisticsVO> order(DateRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        OrderStatisticsVO statistics = orderService.orderStatistics(request);
        return RespBody.success(statistics);
    }

    @GetMapping("/dayOrder")
    @ApiOperation("下单统计(按天)")
    public RespBody<List<OrderStatisticsVO>> dayOrder(DateRequest request) {
        this.setNull(request);
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<OrderStatisticsVO> statistics = orderService.dayOrder(request);
        return RespBody.success(statistics);
    }

    @GetMapping("/visit")
    @ApiOperation("浏览量")
    public RespBody<List<MemberVisitVO>> visit(VisitRequest request) {
        List<MemberVisitVO> statistics = memberVisitLogService.dayVisit(request);
        return RespBody.success(statistics);
    }

    @GetMapping("/collect")
    @ApiOperation("收藏量")
    public RespBody<List<CollectStatisticsVO>> visit(CollectRequest request) {
        List<CollectStatisticsVO> statistics = memberCollectService.dayCollect(request);
        return RespBody.success(statistics);
    }

    @GetMapping("/append")
    @ApiOperation("新增商品数")
    public RespBody<List<ProductStatisticsVO>> append(ProductRequest request) {
        List<ProductStatisticsVO> statistics = commonService.dayAppend(request);
        return RespBody.success(statistics);
    }

    @GetMapping("/cart")
    @ApiOperation("加购统计")
    public RespBody<List<CartStatisticsVO>> cart(DateRequest request) {
        this.setNull(request);
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<CartStatisticsVO> statistics = shoppingCartService.dayCart(request);
        return RespBody.success(statistics);
    }

    private void setNull(DateRequest request) {
        if (request.getStartDate() == null && request.getEndDate() == null) {
            LocalDate endDate = LocalDate.now().plusDays(1);
            request.setEndDate(endDate);
            request.setStartDate(endDate.minusMonths(1));
        }
    }

}
