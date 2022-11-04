package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.feedback.FeedbackDisposeRequest;
import com.eghm.model.dto.feedback.FeedbackQueryRequest;
import com.eghm.model.vo.feedback.FeedbackVO;
import com.eghm.service.common.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @date 2019/8/28 14:16
 */
@RestController
@Api(tags = "反馈管理")
@AllArgsConstructor
@RequestMapping("/manage/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping("/listPage")
    @ApiOperation("分页查询反馈列表")
    public PageData<FeedbackVO> listPage(FeedbackQueryRequest request) {
        Page<FeedbackVO> byPage = feedbackService.getByPage(request);
        return PageData.toPage(byPage);
    }

    @PostMapping("/dispose")
    @ApiOperation("处理反馈信息")
    public RespBody<Void> dispose(@Validated @RequestBody FeedbackDisposeRequest request) {
        SecurityOperator operator = SecurityOperatorHolder.getRequiredOperator();
        request.setOperatorId(operator.getId());
        request.setOperatorName(operator.getOperatorName());
        feedbackService.dispose(request);
        return RespBody.success();
    }
}
