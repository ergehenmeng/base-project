package com.eghm.web.controller.business;

import com.eghm.dto.business.order.restaurant.VoucherOrderQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.VoucherOrderService;
import com.eghm.vo.business.order.restaurant.VoucherOrderDetailVO;
import com.eghm.vo.business.order.restaurant.VoucherOrderSnapshotVO;
import com.eghm.vo.business.order.restaurant.VoucherOrderVO;
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

    private final VoucherOrderService voucherOrderService;

    @GetMapping("/listPage")
    @ApiOperation("餐饮订单列表")
    public RespBody<List<VoucherOrderVO>> listPage(@Validated VoucherOrderQueryDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        List<VoucherOrderVO> voList = voucherOrderService.getByPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/detail")
    @ApiOperation("餐饮订单详情")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<VoucherOrderDetailVO> detail(@RequestParam("orderNo") String orderNo) {
        VoucherOrderDetailVO detail = voucherOrderService.getDetail(orderNo, ApiHolder.getMemberId());
        return RespBody.success(detail);
    }

    @GetMapping("/snapshot")
    @ApiOperation("餐饮快照详情")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<VoucherOrderSnapshotVO> snapshot(@RequestParam("orderNo") String orderNo) {
        VoucherOrderSnapshotVO detail = voucherOrderService.snapshotDetail(orderNo, ApiHolder.getMemberId());
        return RespBody.success(detail);
    }
}
