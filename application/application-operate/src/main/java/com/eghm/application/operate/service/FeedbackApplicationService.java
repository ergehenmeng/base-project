package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.operate.feedback.FeedbackAddDTO;
import com.eghm.application.shared.dto.operate.feedback.FeedbackDisposeRequest;

/**
 * @author 二哥很猛
 * @since 2019/8/28 10:46
 */
public interface FeedbackApplicationService {

    /**
     * 添加反馈信息
     *
     * @param request 前台参数
     */
    void addFeedback(FeedbackAddDTO request);

    /**
     * 反馈信息处理
     *
     * @param request 处理结果信息
     */
    void dispose(FeedbackDisposeRequest request);
}
