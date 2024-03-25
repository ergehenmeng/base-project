package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CacheConstant;
import com.eghm.dto.business.order.OfflineRefundRequest;
import com.eghm.dto.business.order.OrderDTO;
import com.eghm.dto.business.order.ticket.TicketOrderQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.lock.RedisLock;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.TicketOrderService;
import com.eghm.utils.ExcelUtil;
import com.eghm.vo.business.order.ticket.TicketOrderDetailResponse;
import com.eghm.vo.business.order.ticket.TicketOrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wyb
 * @since 2023/6/8
 */
@RestController
@Api(tags = "门票订单")
@AllArgsConstructor
@RequestMapping(value = "/manage/ticket/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketOrderController {

    private final OrderService orderService;

    private final TicketOrderService ticketOrderService;

    private final RedisLock redisLock;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<TicketOrderResponse>> listPage(TicketOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<TicketOrderResponse> byPage = ticketOrderService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/offlineRefund", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("线下退款")
    public RespBody<Void> offlineRefund(@RequestBody @Validated OfflineRefundRequest request) {
        return redisLock.lock(CacheConstant.MANUAL_REFUND_LOCK + request.getOrderNo(), 10_000, () -> {
            orderService.offlineRefund(request);
            return RespBody.success();
        });
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    public RespBody<TicketOrderDetailResponse> detail(@Validated OrderDTO dto) {
        TicketOrderDetailResponse detail = ticketOrderService.detail(dto.getOrderNo());
        return RespBody.success(detail);
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, TicketOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<TicketOrderResponse> byPage = ticketOrderService.getList(request);
        ExcelUtil.export(response, "门票订单", byPage, TicketOrderResponse.class);
    }
}
