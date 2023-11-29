package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.order.item.ItemOrderQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.OrderService;
import com.eghm.vo.business.order.item.ExpressDetailVO;
import com.eghm.vo.business.order.item.ItemOrderDetailVO;
import com.eghm.vo.business.order.item.ItemOrderVO;
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
@Api(tags = "零售订单")
@AllArgsConstructor
@RequestMapping("/webapp/item/order")
public class ItemOrderController {

    private final ItemOrderService itemOrderService;

    private final OrderService orderService;

    @GetMapping("/listPage")
    @ApiOperation("订单列表")
    public RespBody<List<ItemOrderVO>> listPage(@Validated ItemOrderQueryDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        List<ItemOrderVO> voList = itemOrderService.getByPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/detail")
    @ApiOperation("订单详情")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<ItemOrderDetailVO> detail(@RequestParam("orderNo") String orderNo) {
        ItemOrderDetailVO detail = itemOrderService.getDetail(orderNo, ApiHolder.getMemberId());
        return RespBody.success(detail);
    }

    @GetMapping("/express")
    @ApiOperation("快递信息详情")
    public RespBody<ExpressDetailVO> express(@Validated IdDTO dto) {
        ExpressDetailVO detail = orderService.expressDetail(dto.getId());
        return RespBody.success(detail);
    }
}
