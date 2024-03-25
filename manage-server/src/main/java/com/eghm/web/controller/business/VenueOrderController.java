package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.order.OrderDTO;
import com.eghm.dto.business.order.venue.VenueOrderQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.VenueOrderService;
import com.eghm.vo.business.order.venue.VenueOrderDetailResponse;
import com.eghm.vo.business.order.venue.VenueOrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2024/2/4
 */
@RestController
@Api(tags = "场馆订单")
@AllArgsConstructor
@RequestMapping(value = "/manage/venue/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class VenueOrderController {

    private final VenueOrderService venueOrderService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<VenueOrderResponse>> listPage(@Validated VenueOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<VenueOrderResponse> page = venueOrderService.listPage(request);
        return RespBody.success(PageData.toPage(page));
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    public RespBody<VenueOrderDetailResponse> detail(@Validated OrderDTO dto) {
        VenueOrderDetailResponse detail = venueOrderService.detail(dto.getOrderNo());
        return RespBody.success(detail);
    }
}
