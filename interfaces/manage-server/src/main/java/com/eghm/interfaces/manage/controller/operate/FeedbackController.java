package com.eghm.interfaces.manage.controller.operate;

import com.eghm.dto.ext.Page;
import com.eghm.configuration.authentication.SecurityHolder;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.ext.UserToken;
import com.eghm.dto.operate.feedback.FeedbackDisposeRequest;
import com.eghm.dto.operate.feedback.FeedbackQueryRequest;
import com.eghm.application.operate.service.FeedbackService;
import com.eghm.vo.operate.feedback.FeedbackResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2019/8/28 14:16
 */
@RestController
@AllArgsConstructor
@Tag(name = "反馈管理")
@RequestMapping(value = "/manage/feedback", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<FeedbackResponse>> listPage(@ParameterObject FeedbackQueryRequest request) {
        Page<FeedbackResponse> byPage = feedbackService.getByPage(request);
        return RespBody.success(PageData.convert(byPage));
    }

    @PostMapping(value = "/dispose", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "回复")
    public RespBody<Void> dispose(@Validated @RequestBody FeedbackDisposeRequest request) {
        UserToken user = SecurityHolder.getUserRequired();
        request.setUserId(user.getId());
        request.setUserName(user.getNickName());
        feedbackService.dispose(request);
        return RespBody.success();
    }
}
