package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.LockConstant;
import com.eghm.dto.business.order.OrderDTO;
import com.eghm.dto.business.order.homestay.HomestayOrderConfirmRequest;
import com.eghm.dto.business.order.homestay.HomestayOrderQueryRequest;
import com.eghm.dto.business.order.refund.PlatformRefundRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.lock.RedisLock;
import com.eghm.service.business.HomestayOrderService;
import com.eghm.service.business.OrderProxyService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.order.homestay.HomestayOrderDetailResponse;
import com.eghm.vo.business.order.homestay.HomestayOrderResponse;
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
@Api(tags = "民宿订单")
@AllArgsConstructor
@RequestMapping(value = "/manage/homestay/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class HomestayOrderController {

    private final RedisLock redisLock;

    private final OrderProxyService orderProxyService;

    private final HomestayOrderService homestayOrderService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<HomestayOrderResponse>> listPage(HomestayOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<HomestayOrderResponse> byPage = homestayOrderService.listPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    public RespBody<HomestayOrderDetailResponse> detail(@Validated OrderDTO dto) {
        HomestayOrderDetailResponse detail = homestayOrderService.detail(dto.getOrderNo());
        return RespBody.success(detail);
    }

    @PostMapping(value = "/refund", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("退款")
    public RespBody<Void> refund(@RequestBody @Validated PlatformRefundRequest request) {
        return redisLock.lock(LockConstant.ORDER_LOCK + request.getOrderNo(), 10_000, () -> {
            request.setEvent(HomestayEvent.PLATFORM_REFUND);
            orderProxyService.refund(request);
            return RespBody.success();
        });
    }

    @PostMapping(value = "/confirm", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("确认订单")
    public RespBody<Void> confirm(@RequestBody @Validated HomestayOrderConfirmRequest request) {
        orderProxyService.confirm(request);
        return RespBody.success();
    }

    @GetMapping("/export")
    @ApiOperation("导出Excel")
    public void export(HttpServletResponse response, HomestayOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<HomestayOrderResponse> byPage = homestayOrderService.getList(request);
        EasyExcelUtil.export(response, "民宿订单", byPage, HomestayOrderResponse.class);
    }
}
