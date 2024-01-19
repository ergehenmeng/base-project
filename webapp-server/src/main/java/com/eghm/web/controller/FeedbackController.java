package com.eghm.web.controller;

import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RequestMessage;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.feedback.FeedbackAddDTO;
import com.eghm.service.common.FeedbackService;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
@Api(tags = "问题反馈")
@AllArgsConstructor
@RequestMapping("/webapp/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/submit")
    @ApiOperation("保存反馈信息")
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
