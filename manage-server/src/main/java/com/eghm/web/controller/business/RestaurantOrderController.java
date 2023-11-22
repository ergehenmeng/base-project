package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.order.restaurant.VoucherOrderQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.RestaurantOrderService;
import com.eghm.vo.business.order.restaurant.RestaurantOrderDetailResponse;
import com.eghm.vo.business.order.restaurant.RestaurantOrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyb
 * @since 2023/6/8
 */
@RestController
@Api(tags = "餐饮订单")
@AllArgsConstructor
@RequestMapping("/manage/restaurant/order")
public class RestaurantOrderController {

    private final RestaurantOrderService restaurantOrderService;

    @GetMapping("/listPage")
    @ApiOperation("订单列表")
    public RespBody<PageData<RestaurantOrderResponse>> listPage(VoucherOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<RestaurantOrderResponse> byPage = restaurantOrderService.listPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/detail")
    @ApiOperation("订单详情")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<RestaurantOrderDetailResponse> detail(@RequestParam("orderNo") String orderNo) {
        RestaurantOrderDetailResponse detail = restaurantOrderService.detail(orderNo);
        return RespBody.success(detail);
    }
}
