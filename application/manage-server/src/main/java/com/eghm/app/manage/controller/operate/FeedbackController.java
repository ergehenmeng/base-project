package com.eghm.app.manage.controller.operate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.foundation.core.configuration.authentication.SecurityHolder;
import com.eghm.foundation.core.dto.ext.PageData;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.foundation.core.security.UserToken;
import com.eghm.business.operation.support.dto.FeedbackDisposeRequest;
import com.eghm.business.operation.support.dto.FeedbackQueryRequest;
import com.eghm.business.operation.support.service.FeedbackService;
import com.eghm.business.operation.support.vo.FeedbackResponse;
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
