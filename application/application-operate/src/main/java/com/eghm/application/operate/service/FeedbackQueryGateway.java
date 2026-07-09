package com.eghm.application.operate.service;

import com.eghm.dto.ext.Page;
import com.eghm.dto.operate.feedback.FeedbackQueryRequest;
import com.eghm.vo.operate.feedback.FeedbackResponse;

/**
 * 用户反馈查询端口
 *
 * @author 二哥很猛
 * @since 2019/8/28 10:46
 */
public interface FeedbackQueryGateway {

    Page<FeedbackResponse> getByPage(Page<FeedbackResponse> page, FeedbackQueryRequest request);
}
