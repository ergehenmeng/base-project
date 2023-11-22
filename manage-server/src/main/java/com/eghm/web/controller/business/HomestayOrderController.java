package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.order.homestay.HomestayOrderQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.HomestayOrderService;
import com.eghm.vo.business.order.homestay.HomestayOrderDetailResponse;
import com.eghm.vo.business.order.homestay.HomestayOrderResponse;
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
@Api(tags = "民宿订单")
@AllArgsConstructor
@RequestMapping("/manage/homestay/order")
public class HomestayOrderController {

    private final HomestayOrderService homestayOrderService;

    @GetMapping("/listPage")
    @ApiOperation("订单列表")
    public RespBody<PageData<HomestayOrderResponse>> listPage(HomestayOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<HomestayOrderResponse> byPage = homestayOrderService.listPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/detail")
    @ApiOperation("订单详情")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<HomestayOrderDetailResponse> detail(@RequestParam("orderNo") String orderNo) {
        HomestayOrderDetailResponse detail = homestayOrderService.detail(orderNo);
        return RespBody.success(detail);
    }
}
