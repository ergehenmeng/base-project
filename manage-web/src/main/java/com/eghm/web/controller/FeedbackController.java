package com.eghm.web.controller;

import com.eghm.configuration.security.SecurityOperator;
import com.eghm.configuration.security.SecurityOperatorHolder;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.feedback.FeedbackDisposeRequest;
import com.eghm.model.dto.feedback.FeedbackQueryRequest;
import com.eghm.model.vo.feedback.FeedbackVO;
import com.eghm.service.common.FeedbackService;
import com.eghm.web.annotation.Mark;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2019/8/28 14:16
 */
@RestController
public class FeedbackController {

    private FeedbackService feedbackService;

    @Autowired
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * 分页查询反馈列表
     */
    @GetMapping("/feedback/list_page")
    @ResponseBody
    public Paging<FeedbackVO> listPage(FeedbackQueryRequest request) {
        PageInfo<FeedbackVO> byPage = feedbackService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 反馈处理
     */
    @PostMapping("/feedback/dispose")
    @ResponseBody
    @Mark
    public RespBody<Object> dispose(FeedbackDisposeRequest request) {
        SecurityOperator operator = SecurityOperatorHolder.getRequiredOperator();
        request.setOperatorId(operator.getId());
        request.setOperatorName(operator.getOperatorName());
        feedbackService.dispose(request);
        return RespBody.success();
    }
}
