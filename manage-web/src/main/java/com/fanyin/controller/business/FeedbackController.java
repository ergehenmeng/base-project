package com.fanyin.controller.business;

import com.fanyin.model.dto.business.feedback.FeedbackModel;
import com.fanyin.model.dto.business.feedback.FeedbackQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.service.common.FeedbackService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 二哥很猛
 * @date 2019/8/28 14:16
 */
@Controller
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 分页查询反馈列表
     */
    @RequestMapping("/feedback/list_page")
    public Paging<FeedbackModel> listPage(FeedbackQueryRequest request){
        PageInfo<FeedbackModel> byPage = feedbackService.getByPage(request);
        return new Paging<>(byPage);
    }
}
