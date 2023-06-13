package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.ticket.TicketOrderQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.service.business.TicketOrderService;
import com.eghm.vo.business.order.ticket.TicketOrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyb
 * @since 2023/6/8
 */
@RestController
@Api(tags = "门票订单")
@AllArgsConstructor
@RequestMapping("/manage/scenic/ticket/order")
public class TicketOrderController {

    private final TicketOrderService ticketOrderService;

    @GetMapping("/listPage")
    @ApiOperation("订单列表")
    public PageData<TicketOrderResponse> listPage(TicketOrderQueryRequest request) {
        Page<TicketOrderResponse> byPage = ticketOrderService.getByPage(request);
        return PageData.toPage(byPage);
    }
}
