package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.item.ItemOrderQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ItemOrderService;
import com.eghm.vo.business.order.item.ItemOrderDetailResponse;
import com.eghm.vo.business.order.item.ItemOrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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

    @GetMapping("/listPage")
    @ApiOperation("订单列表")
    public RespBody<PageData<ItemOrderResponse>> listPage(ItemOrderQueryRequest request) {
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

    @PostMapping("/sipping")
    @ApiOperation("发货")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<Void> sipping(@RequestParam("orderNo") String orderNo) {
        // TODo 待补全
        return RespBody.success();
    }
}
