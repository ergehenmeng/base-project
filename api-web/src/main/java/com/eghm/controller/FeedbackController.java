package com.eghm.controller;

import com.eghm.model.dto.business.feedback.FeedbackAddRequest;
import com.eghm.model.ext.RequestThreadLocal;
import com.eghm.model.ext.RespBody;
import com.eghm.service.common.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2019/8/28 13:42
 */
@RestController
@Api(tags = "问题反馈")
@RequestMapping("/api")
public class FeedbackController{

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 移动端保存反馈信息
     */
    @PostMapping("/user/feedback")
    @ApiOperation("保存反馈信息")
    public RespBody<Object> feedback(FeedbackAddRequest request) {
        request.setSystemVersion(RequestThreadLocal.getOsVersion());
        request.setVersion(RequestThreadLocal.getVersion());
        feedbackService.addFeedback(request);
        return RespBody.getInstance();
    }

}
