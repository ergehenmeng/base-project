package com.eghm.interfaces.manage.controller.business;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.IdDTO;
import com.eghm.application.shared.dto.business.pay.PayLogQueryRequest;
import com.eghm.application.shared.dto.ext.PageData;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.payment.model.PayNotifyLog;
import com.eghm.application.payment.query.PayNotifyLogQueryService;
import com.eghm.application.payment.query.PayRequestLogQueryService;
import com.eghm.application.payment.service.PayNotifyLogApplicationService;
import com.eghm.application.shared.vo.operate.log.PayNotifyLogResponse;
import com.eghm.application.shared.vo.operate.log.PayRequestLogResponse;
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

    private final PayNotifyLogApplicationService payNotifyLogService;

    private final PayNotifyLogQueryService payNotifyLogQueryService;

    private final PayRequestLogQueryService payRequestLogQueryService;

    @GetMapping("/sync/listPage")
    @Operation(summary = "支付同步请求日志列表")
    public RespBody<PageData<PayRequestLogResponse>> syncListPage(@ParameterObject PayLogQueryRequest request) {
        Page<PayRequestLogResponse> logPage = payRequestLogQueryService.getByPage(request.createPage(), request);
        return RespBody.success(PageData.convert(logPage));
    }

    @GetMapping("/async/listPage")
    @Operation(summary = "支付异步响应日志列表")
    public RespBody<PageData<PayNotifyLogResponse>> asyncListPage(@ParameterObject PayLogQueryRequest request) {
        Page<PayNotifyLogResponse> logPage = payNotifyLogQueryService.getByPage(request.createPage(), request);
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
