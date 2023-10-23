package com.eghm.web.controller.business;

import com.eghm.dto.business.order.ticket.TicketOrderQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.TicketOrderService;
import com.eghm.vo.business.order.ticket.TicketOrderDetailVO;
import com.eghm.vo.business.order.ticket.TicketOrderVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@AccessToken
@RestController
@Api(tags = "门票订单")
@AllArgsConstructor
@RequestMapping("/webapp/ticket/order")
public class TicketOrderController {

    private final TicketOrderService ticketOrderService;

    @GetMapping("/listPage")
    @ApiOperation("门票订单列表")
    public RespBody<List<TicketOrderVO>> listPage(@Validated TicketOrderQueryDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        List<TicketOrderVO> voList = ticketOrderService.getByPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/detail")
    @ApiOperation("门票订单详情")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<TicketOrderDetailVO> detail(@RequestParam("orderNo") String orderNo) {
        TicketOrderDetailVO detail = ticketOrderService.getDetail(orderNo, ApiHolder.getMemberId());
        return RespBody.success(detail);
    }
}
