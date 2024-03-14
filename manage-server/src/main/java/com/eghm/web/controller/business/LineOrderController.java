package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.order.line.LineOrderQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.LineOrderService;
import com.eghm.utils.ExcelUtil;
import com.eghm.vo.business.order.line.LineOrderDetailResponse;
import com.eghm.vo.business.order.line.LineOrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wyb
 * @since 2023/6/8
 */
@RestController
@Api(tags = "线路订单")
@AllArgsConstructor
@RequestMapping(value = "/manage/line/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class LineOrderController {

    private final LineOrderService lineOrderService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<LineOrderResponse>> listPage(LineOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<LineOrderResponse> byPage = lineOrderService.listPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true)
    public RespBody<LineOrderDetailResponse> detail(@RequestParam("orderNo") String orderNo) {
        LineOrderDetailResponse detail = lineOrderService.detail(orderNo);
        return RespBody.success(detail);
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, LineOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<LineOrderResponse> byPage = lineOrderService.getList(request);
        ExcelUtil.export(response, "线路订单", byPage, LineOrderResponse.class);
    }
}
