package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.ext.UserToken;
import com.eghm.dto.feedback.FeedbackDisposeRequest;
import com.eghm.dto.feedback.FeedbackQueryRequest;
import com.eghm.service.common.FeedbackService;
import com.eghm.vo.feedback.FeedbackVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2019/8/28 14:16
 */
@RestController
@Api(tags = "反馈管理")
@AllArgsConstructor
@RequestMapping("/manage/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<FeedbackVO>> listPage(FeedbackQueryRequest request) {
        Page<FeedbackVO> byPage = feedbackService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping("/dispose")
    @ApiOperation("回复")
    public RespBody<Void> dispose(@Validated @RequestBody FeedbackDisposeRequest request) {
        UserToken user = SecurityHolder.getUserRequired();
        request.setUserId(user.getId());
        request.setUserName(user.getNickName());
        feedbackService.dispose(request);
        return RespBody.success();
    }
}
