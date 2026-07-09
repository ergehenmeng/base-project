package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.operate.feedback.FeedbackAddDTO;
import com.eghm.application.shared.dto.operate.feedback.FeedbackDisposeRequest;
import com.eghm.application.shared.dto.operate.feedback.FeedbackQueryRequest;
import com.eghm.application.shared.vo.operate.feedback.FeedbackResponse;

/**
 * @author 二哥很猛
 * @since 2019/8/28 10:46
 */
public interface FeedbackApplicationService {

    /**
     * 分页查询用户反馈列表
     *
     * @param request 查询条件
     * @return 反馈信息 包含用户基本信息
     */
    Page<FeedbackResponse> getByPage(FeedbackQueryRequest request);

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
