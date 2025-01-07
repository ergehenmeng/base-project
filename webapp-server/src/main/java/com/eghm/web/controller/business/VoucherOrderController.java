package com.eghm.web.controller.business;

import com.eghm.dto.business.order.OrderDTO;
import com.eghm.dto.business.order.voucher.VoucherOrderQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.VisitType;
import com.eghm.service.business.VoucherOrderService;
import com.eghm.vo.business.order.restaurant.VoucherOrderDetailVO;
import com.eghm.vo.business.order.restaurant.VoucherOrderSnapshotVO;
import com.eghm.vo.business.order.restaurant.VoucherOrderVO;
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
@Tag(name="餐饮订单")
@AllArgsConstructor
@RequestMapping(value = "/webapp/voucher/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoucherOrderController {

    private final VoucherOrderService voucherOrderService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<List<VoucherOrderVO>> listPage(@Validated VoucherOrderQueryDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        List<VoucherOrderVO> voList = voucherOrderService.getByPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    @VisitRecord(VisitType.ORDER_DETAIL)
    public RespBody<VoucherOrderDetailVO> detail(@Validated OrderDTO dto) {
        VoucherOrderDetailVO detail = voucherOrderService.getDetail(dto.getOrderNo(), ApiHolder.getMemberId());
        return RespBody.success(detail);
    }

    @GetMapping("/snapshot")
    @Operation(summary = "快照详情")
    public RespBody<VoucherOrderSnapshotVO> snapshot(@Validated OrderDTO dto) {
        VoucherOrderSnapshotVO detail = voucherOrderService.snapshotDetail(dto.getOrderNo(), ApiHolder.getMemberId());
        return RespBody.success(detail);
    }
}
