package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.LockConstant;
import com.eghm.dto.business.order.OrderDTO;
import com.eghm.dto.business.order.refund.PlatformRefundRequest;
import com.eghm.dto.business.order.venue.VenueOrderQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.event.impl.VenueEvent;
import com.eghm.lock.RedisLock;
import com.eghm.service.business.OrderProxyService;
import com.eghm.service.business.VenueOrderService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.order.venue.VenueOrderDetailResponse;
import com.eghm.vo.business.order.venue.VenueOrderResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/4
 */
@RestController
@Tag(name="场馆订单")
@AllArgsConstructor
@RequestMapping(value = "/manage/venue/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class VenueOrderController {

    private final RedisLock redisLock;

    private final OrderProxyService orderProxyService;

    private final VenueOrderService venueOrderService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<VenueOrderResponse>> listPage(@ParameterObject VenueOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<VenueOrderResponse> page = venueOrderService.listPage(request);
        return RespBody.success(PageData.toPage(page));
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    public RespBody<VenueOrderDetailResponse> detail(@ParameterObject @Validated OrderDTO dto) {
        VenueOrderDetailResponse detail = venueOrderService.detail(dto.getOrderNo());
        return RespBody.success(detail);
    }

    @PostMapping(value = "/refund", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "退款")
    public RespBody<Void> refund(@RequestBody @Validated PlatformRefundRequest request) {
        return redisLock.lock(LockConstant.ORDER_LOCK + request.getOrderNo(), 10_000, () -> {
            request.setEvent(VenueEvent.PLATFORM_REFUND);
            orderProxyService.refund(request);
            return RespBody.success();
        });
    }

    @GetMapping("/export")
    @Operation(summary = "导出")
    public void export(HttpServletResponse response, @ParameterObject VenueOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<VenueOrderResponse> byPage = venueOrderService.getList(request);
        EasyExcelUtil.export(response, "场馆订单", byPage, VenueOrderResponse.class);
    }
}
