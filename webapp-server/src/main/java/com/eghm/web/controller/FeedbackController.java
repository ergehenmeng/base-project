package com.eghm.web.controller;

import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.RequestMessage;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.feedback.FeedbackAddDTO;
import com.eghm.service.common.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author 二哥很猛
 * @date 2019/8/28 13:42
 */
@RestController
@Api(tags = "问题反馈")
@AllArgsConstructor
@RequestMapping("/webapp/feedback")
public class FeedbackController{

    private final FeedbackService feedbackService;

    /**
     * 移动端保存反馈信息
     */
    @PostMapping("/submit")
    @ApiOperation("保存反馈信息")
    public RespBody<Void> submit(@RequestBody @Valid FeedbackAddDTO request) {
        RequestMessage message = ApiHolder.get();
        request.setSystemVersion(message.getOsVersion());
        request.setVersion(message.getVersion());
        request.setUserId(message.getUserId());
        request.setDeviceBrand(message.getDeviceBrand());
        request.setDeviceModel(message.getDeviceModel());
        feedbackService.addFeedback(request);
        return RespBody.success();
    }

}
