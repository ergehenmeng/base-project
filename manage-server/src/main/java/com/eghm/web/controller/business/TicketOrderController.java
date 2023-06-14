package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.ticket.TicketOfflineRefundRequest;
import com.eghm.dto.business.order.ticket.TicketOrderQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.TicketOrderService;
import com.eghm.vo.business.order.ticket.TicketOrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wyb
 * @since 2023/6/8
 */
@RestController
@Api(tags = "门票订单")
@AllArgsConstructor
@RequestMapping("/manage/scenic/ticket/order")
public class TicketOrderController {

    private final OrderService orderService;

    private final TicketOrderService ticketOrderService;

    @GetMapping("/listPage")
    @ApiOperation("订单列表")
    public PageData<TicketOrderResponse> listPage(TicketOrderQueryRequest request) {
        Page<TicketOrderResponse> byPage = ticketOrderService.getByPage(request);
        return PageData.toPage(byPage);
    }

    @PostMapping("/offlineRefund")
    @ApiOperation("线下退款")
    public RespBody<Void> offlineRefund(@RequestBody @Validated TicketOfflineRefundRequest request) {
        orderService.ticketOfflineRefund(request);
        return RespBody.success();
    }
}
