package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CacheConstant;
import com.eghm.dto.business.order.OrderDTO;
import com.eghm.dto.business.order.item.ItemExpressRequest;
import com.eghm.dto.business.order.item.ItemOrderQueryRequest;
import com.eghm.dto.business.order.item.ItemSippingRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.Express;
import com.eghm.service.business.ItemExpressService;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.OrderProxyService;
import com.eghm.service.business.OrderService;
import com.eghm.cache.CacheProxyService;
import com.eghm.lock.RedisLock;
import com.eghm.utils.ExcelUtil;
import com.eghm.vo.business.order.item.ItemOrderDetailResponse;
import com.eghm.vo.business.order.item.ItemOrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@Api(tags = "零售订单")
@AllArgsConstructor
@RequestMapping(value = "/manage/item/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemOrderController {

    private final ItemExpressService itemExpressService;

    private final ItemOrderService itemOrderService;

    private final OrderService orderService;

    private final RedisLock redisLock;

    private final CacheProxyService cacheProxyService;

    private final OrderProxyService orderProxyService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<ItemOrderResponse>> listPage(ItemOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<ItemOrderResponse> byPage = itemOrderService.listPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<ItemOrderDetailResponse> detail(@RequestParam("orderNo") String orderNo) {
        ItemOrderDetailResponse detail = itemOrderService.detail(orderNo);
        return RespBody.success(detail);
    }

    /**
     * 1. 前端需要提醒各个商品发货的数量等信息(防止重复发货/退款发货)
     * 2. 针对不需要发货的商品, 商品无法勾选发货
     */
    @PostMapping(value = "/sipping", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("发货")
    public RespBody<Void> sipping(@RequestBody @Validated ItemSippingRequest request) {
        return redisLock.lock(CacheConstant.SIPPING_LOCK + request.getOrderNo(), 10_000, () -> {
            orderService.sipping(request);
            return RespBody.success();
        });
    }

    @GetMapping("/expressList")
    @ApiOperation("获取快递列表")
    public RespBody<List<Express>> getExpressList() {
        return RespBody.success(cacheProxyService.getExpressList());
    }

    @PostMapping(value = "/updateExpress", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新快递单号")
    public RespBody<Void> updateExpress(@RequestBody @Validated ItemExpressRequest request) {
        itemExpressService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/refund", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("退款")
    public RespBody<Void> refund(@RequestBody @Validated OrderDTO request) {
        return redisLock.lock(CacheConstant.ORDER_REFUND + request.getOrderNo(), 10_000, () -> {
            orderProxyService.refund(request.getOrderNo());
            return RespBody.success();
        });
    }

    @GetMapping("/export")
    @ApiOperation("导出Excel")
    public void export(HttpServletResponse response, ItemOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<ItemOrderResponse> byPage = itemOrderService.getList(request);
        ExcelUtil.export(response, "零售订单", byPage, ItemOrderResponse.class);
    }

}
