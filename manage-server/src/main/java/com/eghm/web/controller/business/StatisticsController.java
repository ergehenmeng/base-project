package com.eghm.web.controller.business;

import com.eghm.dto.DateRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.OrderService;
import com.eghm.service.member.MemberService;
import com.eghm.vo.business.order.OrderStatisticsVO;
import com.eghm.vo.member.MemberRegisterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/22
 */

@RestController
@Api(tags = "统计分析")
@AllArgsConstructor
@RequestMapping("/manage/statistics")
public class StatisticsController {

    private final MemberService memberService;

    private final OrderService orderService;

    @GetMapping("/register")
    @ApiOperation("注册统计")
    public RespBody<Integer> register(DateRequest request) {
        Integer statistics = memberService.registerStatistics(request);
        return RespBody.success(statistics);
    }

    @GetMapping("/dayRegister")
    @ApiOperation("注册统计(按天)")
    public RespBody<List<MemberRegisterVO>> dayRegister(DateRequest request) {
        List<MemberRegisterVO> statistics = memberService.dayRegister(request);
        return RespBody.success(statistics);
    }

    @GetMapping("/order")
    @ApiOperation("下单统计")
    public RespBody<OrderStatisticsVO> order(DateRequest request) {
        OrderStatisticsVO statistics = orderService.orderStatistics(request);
        return RespBody.success(statistics);
    }

    @GetMapping("/dayOrder")
    @ApiOperation("下单统计(按天)")
    public RespBody<List<OrderStatisticsVO>> dayOrder(DateRequest request) {
        List<OrderStatisticsVO> statistics = orderService.dayOrder(request);
        return RespBody.success(statistics);
    }

}
