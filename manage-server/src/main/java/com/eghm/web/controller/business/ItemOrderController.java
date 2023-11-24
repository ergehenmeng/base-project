package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CacheConstant;
import com.eghm.dto.business.order.OfflineRefundRequest;
import com.eghm.dto.business.order.OnlineRefundRequest;
import com.eghm.dto.business.order.item.ItemOrderQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.OrderService;
import com.eghm.service.cache.RedisLock;
import com.eghm.vo.business.order.item.ItemOrderDetailResponse;
import com.eghm.vo.business.order.item.ItemOrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wyb
 * @since 2023/6/8
 */
@RestController
@Api(tags = "零售订单")
@AllArgsConstructor
@RequestMapping("/manage/item/order")
public class ItemOrderController {

    private final ItemOrderService itemOrderService;

    private final OrderService orderService;

    private final RedisLock redisLock;

    @GetMapping("/listPage")
    @ApiOperation("订单列表")
    public RespBody<PageData<ItemOrderResponse>> listPage(ItemOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<ItemOrderResponse> byPage = itemOrderService.listPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/detail")
    @ApiOperation("订单详情")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<ItemOrderDetailResponse> detail(@RequestParam("orderNo") String orderNo) {
        ItemOrderDetailResponse detail = itemOrderService.detail(orderNo);
        return RespBody.success(detail);
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

    @PostMapping("/sipping")
    @ApiOperation("发货")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<Void> sipping(@RequestParam("orderNo") String orderNo) {
        // TODo 待补全
        return RespBody.success();
    }
}
