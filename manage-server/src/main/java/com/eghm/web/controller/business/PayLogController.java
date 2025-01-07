package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.log.PayLogQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.pay.PayNotifyLogService;
import com.eghm.pay.PayRequestLogService;
import com.eghm.vo.business.log.PayNotifyLogResponse;
import com.eghm.vo.business.log.PayRequestLogResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyb
 * @since 2023/6/13
 */
@RestController
@Tag(name="支付日志")
@AllArgsConstructor
@RequestMapping(value = "/manage/pay/log", produces = MediaType.APPLICATION_JSON_VALUE)
public class PayLogController {

    private final PayNotifyLogService payNotifyLogService;

    private final PayRequestLogService payRequestLogService;

    @GetMapping("/sync/listPage")
    @Operation(summary = "支付同步请求日志列表")
    public RespBody<PageData<PayRequestLogResponse>> syncListPage(@ParameterObject PayLogQueryRequest request) {
        Page<PayRequestLogResponse> merchantPage = payRequestLogService.getByPage(request);
        return RespBody.success(PageData.toPage(merchantPage));
    }

    @GetMapping("/async/listPage")
    @Operation(summary = "支付异步响应日志列表")
    public RespBody<PageData<PayNotifyLogResponse>> asyncListPage(@ParameterObject PayLogQueryRequest request) {
        Page<PayNotifyLogResponse> merchantPage = payNotifyLogService.getByPage(request);
        return RespBody.success(PageData.toPage(merchantPage));
    }

}
