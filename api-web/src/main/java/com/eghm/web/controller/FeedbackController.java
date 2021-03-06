package com.eghm.web.controller;

import com.eghm.model.dto.feedback.FeedbackAddDTO;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.RequestMessage;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.common.FeedbackService;
import com.eghm.web.annotation.SkipLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2019/8/28 13:42
 */
@RestController
@Api(tags = "问题反馈")
public class FeedbackController{

    private FeedbackService feedbackService;

    @Autowired
    @SkipLogger
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * 移动端保存反馈信息
     */
    @PostMapping("/feedback/submit")
    @ApiOperation("保存反馈信息")
    public RespBody<Object> submit(FeedbackAddDTO request) {
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
