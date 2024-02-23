package com.eghm.web.controller.business;

import com.eghm.dto.business.order.homestay.HomestayOrderQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.HomestayOrderService;
import com.eghm.vo.business.order.homestay.HomestayOrderDetailVO;
import com.eghm.vo.business.order.homestay.HomestayOrderSnapshotVO;
import com.eghm.vo.business.order.homestay.HomestayOrderVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
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
@Api(tags = "民宿订单")
@AllArgsConstructor
@RequestMapping(value = "/webapp/homestay/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class HomestayOrderController {

    private final HomestayOrderService homestayOrderService;

    @GetMapping("/listPage")
    @ApiOperation("民宿订单列表")
    public RespBody<List<HomestayOrderVO>> listPage(@Validated HomestayOrderQueryDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        List<HomestayOrderVO> voList = homestayOrderService.getByPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/detail")
    @ApiOperation("民宿订单详情")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<HomestayOrderDetailVO> detail(@RequestParam("orderNo") String orderNo) {
        HomestayOrderDetailVO detail = homestayOrderService.getDetail(orderNo, ApiHolder.getMemberId());
        return RespBody.success(detail);
    }

    @GetMapping("/snapshot")
    @ApiOperation("民宿快照详情")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<HomestayOrderSnapshotVO> snapshot(@RequestParam("orderNo") String orderNo) {
        HomestayOrderSnapshotVO detail = homestayOrderService.snapshotDetail(orderNo, ApiHolder.getMemberId());
        return RespBody.success(detail);
    }
}
