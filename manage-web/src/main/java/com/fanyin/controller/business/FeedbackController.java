package com.fanyin.controller.business;

import com.fanyin.controller.AbstractController;
import com.fanyin.model.dto.business.feedback.FeedbackDisposeRequest;
import com.fanyin.model.dto.business.feedback.FeedbackQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.model.ext.RespBody;
import com.fanyin.model.vo.feedback.FeedbackVO;
import com.fanyin.service.common.FeedbackService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 二哥很猛
 * @date 2019/8/28 14:16
 */
@Controller
public class FeedbackController extends AbstractController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 分页查询反馈列表
     */
    @PostMapping("/feedback/list_page")
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
    public RespBody dispose(FeedbackDisposeRequest request) {
        request.setOperatorId(getOperatorId());
        request.setOperatorName(getOperatorName());
        feedbackService.dispose(request);
        return RespBody.getInstance();
    }
}
