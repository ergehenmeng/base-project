package com.eghm.interfaces.webapp.controller;

import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.application.shared.dto.ext.RequestMessage;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.shared.dto.operate.feedback.FeedbackAddDTO;
import com.eghm.application.operate.service.FeedbackApplicationService;
import com.eghm.interfaces.webapp.annotation.AccessToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2019/8/28 13:42
 */
@RestController
@Tag(name = "问题反馈")
@AllArgsConstructor
@RequestMapping(value = "/webapp/feedback", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedbackController {

    private final FeedbackApplicationService feedbackService;

    @PostMapping(value = "/submit", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "保存反馈信息")
    @AccessToken
    public RespBody<Void> submit(@RequestBody @Validated FeedbackAddDTO request) {
        RequestMessage message = ApiHolder.get();
        request.setSystemVersion(message.getOsVersion());
        request.setVersion(message.getVersion());
        request.setMemberId(message.getMemberId());
        request.setDeviceBrand(message.getDeviceBrand());
        request.setDeviceModel(message.getDeviceModel());
        feedbackService.addFeedback(request);
        return RespBody.success();
    }

}
