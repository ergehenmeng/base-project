package com.eghm.web.controller.business;

import com.eghm.dto.business.order.OrderDTO;
import com.eghm.dto.business.order.venue.VenueOrderQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.VisitType;
import com.eghm.service.business.VenueOrderService;
import com.eghm.vo.business.order.venue.VenueOrderDetailVO;
import com.eghm.vo.business.order.venue.VenueOrderVO;
import com.eghm.web.annotation.AccessToken;
import com.eghm.web.annotation.VisitRecord;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@AccessToken
@RestController
@Tag(name="场馆订单")
@AllArgsConstructor
@RequestMapping(value = "/webapp/venue/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class VenueOrderController {

    private final VenueOrderService venueOrderService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<List<VenueOrderVO>> listPage(@Validated VenueOrderQueryDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        List<VenueOrderVO> voList = venueOrderService.getByPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    @VisitRecord(VisitType.ORDER_DETAIL)
    public RespBody<VenueOrderDetailVO> detail(@Validated OrderDTO dto) {
        VenueOrderDetailVO detail = venueOrderService.getDetail(dto.getOrderNo(), ApiHolder.getMemberId());
        return RespBody.success(detail);
    }
}
