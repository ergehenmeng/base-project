package com.eghm.app.manage.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.foundation.core.dto.IdDTO;
import com.eghm.integration.payment.dto.PayLogQueryRequest;
import com.eghm.foundation.core.dto.ext.PageData;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.integration.payment.entity.PayNotifyLog;
import com.eghm.integration.payment.service.PayNotifyLogService;
import com.eghm.integration.payment.service.PayRequestLogService;
import com.eghm.integration.payment.vo.PayNotifyLogResponse;
import com.eghm.integration.payment.vo.PayRequestLogResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wyb
 * @since 2023/6/13
 */
@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "支付日志")
@RequestMapping(value = "/manage/pay/log", produces = MediaType.APPLICATION_JSON_VALUE)
public class PayLogController {

    private final PayNotifyLogService payNotifyLogService;

    private final PayRequestLogService payRequestLogService;

    @GetMapping("/sync/listPage")
    @Operation(summary = "支付同步请求日志列表")
    public RespBody<PageData<PayRequestLogResponse>> syncListPage(@ParameterObject PayLogQueryRequest request) {
        Page<PayRequestLogResponse> logPage = payRequestLogService.getByPage(request);
        return RespBody.success(PageData.convert(logPage));
    }

    @GetMapping("/async/listPage")
    @Operation(summary = "支付异步响应日志列表")
    public RespBody<PageData<PayNotifyLogResponse>> asyncListPage(@ParameterObject PayLogQueryRequest request) {
        Page<PayNotifyLogResponse> logPage = payNotifyLogService.getByPage(request);
        return RespBody.success(PageData.convert(logPage));
    }

    @PostMapping("/async/playback")
    @Operation(summary = "异步回调回放")
    public RespBody<Void> playback(@RequestBody @Validated IdDTO dto) {
        PayNotifyLog notifyLog = payNotifyLogService.selectById(dto.getId());
        if (notifyLog == null) {
            return RespBody.error(ErrorCode.NOTIFY_LOG_NULL);
        }
        if (notifyLog.getState() == 1) {
            return RespBody.error(ErrorCode.NOTIFY_CALLBACK_SUCCESS);
        }
        log.info("待补全异步回调回放逻辑, id:[{}]", dto.getId());
        payNotifyLogService.playbackSuccess(dto.getId());
        return RespBody.success();
    }
}
