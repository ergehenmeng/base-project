package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.pay.PayLogQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.pay.service.PayNotifyLogService;
import com.eghm.pay.service.PayRequestLogService;
import com.eghm.vo.operate.log.PayNotifyLogResponse;
import com.eghm.vo.operate.log.PayRequestLogResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyb
 * @since 2023/6/13
 */
@RestController
@Api(tags = "支付日志")
@AllArgsConstructor
@RequestMapping(value = "/manage/pay/log", produces = MediaType.APPLICATION_JSON_VALUE)
public class PayLogController {

    private final PayNotifyLogService payNotifyLogService;

    private final PayRequestLogService payRequestLogService;

    @GetMapping("/sync/listPage")
    @ApiOperation("支付同步请求日志列表")
    public RespBody<PageData<PayRequestLogResponse>> syncListPage(PayLogQueryRequest request) {
        Page<PayRequestLogResponse> logPage = payRequestLogService.getByPage(request);
        return RespBody.success(PageData.toPage(logPage));
    }

    @GetMapping("/async/listPage")
    @ApiOperation("支付异步响应日志列表")
    public RespBody<PageData<PayNotifyLogResponse>> asyncListPage(PayLogQueryRequest request) {
        Page<PayNotifyLogResponse> logPage = payNotifyLogService.getByPage(request);
        return RespBody.success(PageData.toPage(logPage));
    }

}
