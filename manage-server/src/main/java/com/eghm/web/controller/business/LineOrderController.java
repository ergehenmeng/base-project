package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.LockConstant;
import com.eghm.dto.business.order.OrderDTO;
import com.eghm.dto.business.order.line.LineOrderQueryRequest;
import com.eghm.dto.business.order.refund.PlatformRefundRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.event.impl.LineEvent;
import com.eghm.lock.RedisLock;
import com.eghm.service.business.LineOrderService;
import com.eghm.service.business.OrderProxyService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.order.line.LineOrderDetailResponse;
import com.eghm.vo.business.order.line.LineOrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    private final RedisLock redisLock;

    private final LineOrderService lineOrderService;

    private final OrderProxyService orderProxyService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<LineOrderResponse>> listPage(LineOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<LineOrderResponse> byPage = lineOrderService.listPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    public RespBody<LineOrderDetailResponse> detail(@Validated OrderDTO dto) {
        LineOrderDetailResponse detail = lineOrderService.detail(dto.getOrderNo());
        return RespBody.success(detail);
    }

    @PostMapping(value = "/refund", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("退款")
    public RespBody<Void> refund(@RequestBody @Validated PlatformRefundRequest request) {
        return redisLock.lock(LockConstant.ORDER_LOCK + request.getOrderNo(), 10_000, () -> {
            request.setEvent(LineEvent.PLATFORM_REFUND);
            orderProxyService.refund(request);
            return RespBody.success();
        });
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, LineOrderQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<LineOrderResponse> byPage = lineOrderService.getList(request);
        EasyExcelUtil.export(response, "线路订单", byPage, LineOrderResponse.class);
    }
}
