package com.eghm.interfaces.manage.controller.operate;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.configuration.authentication.SecurityHolder;
import com.eghm.application.shared.dto.ext.PageData;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.shared.dto.ext.UserToken;
import com.eghm.application.shared.dto.operate.feedback.FeedbackDisposeRequest;
import com.eghm.application.shared.dto.operate.feedback.FeedbackQueryRequest;
import com.eghm.application.operate.query.FeedbackQueryService;
import com.eghm.application.operate.service.FeedbackApplicationService;
import com.eghm.application.shared.vo.operate.feedback.FeedbackResponse;
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

    private final FeedbackApplicationService feedbackService;

    private final FeedbackQueryService feedbackQueryService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<FeedbackResponse>> listPage(@ParameterObject FeedbackQueryRequest request) {
        Page<FeedbackResponse> byPage = feedbackQueryService.getByPage(request.createPage(), request);
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
