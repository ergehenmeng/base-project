package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.order.item.ConfirmReceiptDTO;
import com.eghm.dto.business.order.item.ItemOrderQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.OrderService;
import com.eghm.vo.business.order.item.*;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@AccessToken
@RestController
@Api(tags = "零售订单")
@AllArgsConstructor
@RequestMapping(value = "/webapp/item/order", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping("/snapshotList")
    @ApiOperation("快照列表")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<List<ItemOrderListVO>> snapshotList(@RequestParam("orderNo") String orderNo) {
        List<ItemOrderListVO> detailList = itemOrderService.getItemList(orderNo);
        return RespBody.success(detailList);
    }

    @GetMapping("/snapshot")
    @ApiOperation("快照详情")
    @ApiImplicitParam(name = "orderId", value = "订单id", required = true)
    public RespBody<ItemOrderSnapshotVO> snapshot(@RequestParam("orderId") Long orderId) {
        ItemOrderSnapshotVO detail = itemOrderService.getSnapshot(orderId, ApiHolder.getMemberId());
        return RespBody.success(detail);
    }

    @PostMapping(value = "/confirmReceipt", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("确认收货")
    public RespBody<Void> confirmReceipt(@Validated @RequestBody ConfirmReceiptDTO dto) {
        orderService.confirmReceipt(dto.getOrderNo(), ApiHolder.getMemberId());
        return RespBody.success();
    }
}
