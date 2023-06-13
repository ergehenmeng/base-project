package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.log.PayLogQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.model.PayNotifyLog;
import com.eghm.model.PayRequestLog;
import com.eghm.service.pay.PayNotifyLogService;
import com.eghm.service.pay.PayRequestLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
@RequestMapping("/manage/pay/log/")
public class PayLogController {

    private final PayRequestLogService payRequestLogService;

    private final PayNotifyLogService payNotifyLogService;

    @GetMapping("/sync/listPage")
    @ApiOperation("支付同步请求日志列表")
    public PageData<PayRequestLog> syncListPage(PayLogQueryRequest request) {
        Page<PayRequestLog> merchantPage = payRequestLogService.getByPage(request);
        return PageData.toPage(merchantPage);
    }

    @GetMapping("/notify/listPage")
    @ApiOperation("支付异步响应日志列表")
    public PageData<PayNotifyLog> notifyListPage(PayLogQueryRequest request) {
        Page<PayNotifyLog> merchantPage = payNotifyLogService.getByPage(request);
        return PageData.toPage(merchantPage);
    }

}
