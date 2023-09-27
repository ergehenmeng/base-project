package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constant.CacheConstant;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.order.ticket.OfflineRefundRequest;
import com.eghm.dto.business.order.ticket.OnlineRefundRequest;
import com.eghm.dto.business.order.ticket.TicketOrderQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.TicketOrderService;
import com.eghm.service.cache.RedisLock;
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

    private final RedisLock redisLock;

    @GetMapping("/listPage")
    @ApiOperation("订单列表")
    public RespBody<PageData<TicketOrderResponse>> listPage(TicketOrderQueryRequest request) {
        Page<TicketOrderResponse> byPage = ticketOrderService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping("/offlineRefund")
    @ApiOperation("线下退款")
    public RespBody<Void> offlineRefund(@RequestBody @Validated OfflineRefundRequest request) {
        redisLock.lock(CacheConstant.MANUAL_REFUND_LOCK + request.getOrderNo(), 10_000, () -> {
            orderService.offlineRefund(request);
            return null;
        });
        return RespBody.success();
    }

    @PostMapping("/onlineRefund")
    @ApiOperation("线上退款")
    public RespBody<Void> onlineRefund(@RequestBody @Validated OnlineRefundRequest request) {
        redisLock.lock(CacheConstant.MANUAL_REFUND_LOCK + request.getOrderNo(), 10_000, () -> {
            orderService.onlineRefund(request);
            return null;
        });
        return RespBody.success();
    }

    @GetMapping("/detail")
    @ApiOperation("订单详情")
    public RespBody<Void> detail(@Validated IdDTO dto) {
        // TODO 待完善
        return RespBody.success();
    }
}
