package com.eghm.web.controller.business;

import com.eghm.dto.business.order.venue.VenueOrderQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.VenueOrderService;
import com.eghm.vo.business.order.venue.VenueOrderDetailVO;
import com.eghm.vo.business.order.venue.VenueOrderVO;
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
@Api(tags = "场馆预约订单")
@AllArgsConstructor
@RequestMapping(value = "/webapp/venue/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class VenueOrderController {

    private final VenueOrderService venueOrderService;

    @GetMapping("/listPage")
    @ApiOperation("订单列表")
    public RespBody<List<VenueOrderVO>> listPage(@Validated VenueOrderQueryDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        List<VenueOrderVO> voList = venueOrderService.getByPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/detail")
    @ApiOperation("订单详情")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<VenueOrderDetailVO> detail(@RequestParam("orderNo") String orderNo) {
        VenueOrderDetailVO detail = venueOrderService.getDetail(orderNo, ApiHolder.getMemberId());
        return RespBody.success(detail);
    }
}
