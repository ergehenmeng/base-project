package com.eghm.web.controller.business;

import com.eghm.dto.business.order.restaurant.VoucherOrderQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.RestaurantOrderService;
import com.eghm.vo.business.order.restaurant.RestaurantOrderDetailVO;
import com.eghm.vo.business.order.restaurant.RestaurantOrderVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@AccessToken
@RestController
@Api(tags = "餐饮订单")
@AllArgsConstructor
@RequestMapping("/webapp/voucher/order")
public class VoucherOrderController {

    private final RestaurantOrderService restaurantOrderService;

    @GetMapping("/listPage")
    @ApiOperation("餐饮订单列表")
    public RespBody<List<RestaurantOrderVO>> listPage(@Validated VoucherOrderQueryDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        List<RestaurantOrderVO> voList = restaurantOrderService.getByPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/detail")
    @ApiOperation("餐饮订单详情")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<RestaurantOrderDetailVO> detail(@RequestParam("orderNo") String orderNo) {
        RestaurantOrderDetailVO detail = restaurantOrderService.getDetail(orderNo, ApiHolder.getMemberId());
        return RespBody.success(detail);
    }
}
