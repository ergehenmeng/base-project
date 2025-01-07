package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.LockConstant;
import com.eghm.dto.business.order.OrderDTO;
import com.eghm.dto.business.order.refund.PlatformRefundRequest;
import com.eghm.dto.business.order.voucher.VoucherOrderQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.event.impl.VoucherEvent;
import com.eghm.lock.RedisLock;
import com.eghm.service.business.OrderProxyService;
import com.eghm.service.business.VoucherOrderService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.order.restaurant.VoucherOrderDetailResponse;
import com.eghm.vo.business.order.restaurant.VoucherOrderResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wyb
 * @since 2023/6/8
 */
@RestController
@Tag(name="餐饮订单")
@AllArgsConstructor
@RequestMapping(value = "/manage/voucher/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoucherOrderController {

    private final RedisLock redisLock;

    private final OrderProxyService orderProxyService;

    private final VoucherOrderService voucherOrderService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<VoucherOrderResponse>> listPage(VoucherOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<VoucherOrderResponse> byPage = voucherOrderService.listPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    public RespBody<VoucherOrderDetailResponse> detail(@Validated OrderDTO dto) {
        VoucherOrderDetailResponse detail = voucherOrderService.detail(dto.getOrderNo());
        return RespBody.success(detail);
    }

    @PostMapping(value = "/refund", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "退款")
    public RespBody<Void> refund(@RequestBody @Validated PlatformRefundRequest request) {
        return redisLock.lock(LockConstant.ORDER_LOCK + request.getOrderNo(), 10_000, () -> {
            request.setEvent(VoucherEvent.PLATFORM_REFUND);
            orderProxyService.refund(request);
            return RespBody.success();
        });
    }

    @GetMapping("/export")
    @Operation(summary = "导出")
    public void export(HttpServletResponse response, VoucherOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<VoucherOrderResponse> byPage = voucherOrderService.getList(request);
        EasyExcelUtil.export(response, "餐饮订单", byPage, VoucherOrderResponse.class);
    }
}
