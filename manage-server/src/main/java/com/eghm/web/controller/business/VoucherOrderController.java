package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.order.OrderDTO;
import com.eghm.dto.business.order.voucher.VoucherOrderQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.VoucherOrderService;
import com.eghm.utils.ExcelUtil;
import com.eghm.vo.business.order.restaurant.VoucherOrderDetailResponse;
import com.eghm.vo.business.order.restaurant.VoucherOrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wyb
 * @since 2023/6/8
 */
@RestController
@Api(tags = "餐饮订单")
@AllArgsConstructor
@RequestMapping(value = "/manage/voucher/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoucherOrderController {

    private final VoucherOrderService voucherOrderService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<VoucherOrderResponse>> listPage(VoucherOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<VoucherOrderResponse> byPage = voucherOrderService.listPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    public RespBody<VoucherOrderDetailResponse> detail(@Validated OrderDTO dto) {
        VoucherOrderDetailResponse detail = voucherOrderService.detail(dto.getOrderNo());
        return RespBody.success(detail);
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, VoucherOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<VoucherOrderResponse> byPage = voucherOrderService.getList(request);
        ExcelUtil.export(response, "餐饮订单", byPage, VoucherOrderResponse.class);
    }
}
