package com.fanyin.service.common;

import com.fanyin.model.dto.business.feedback.FeedbackAddRequest;
import com.fanyin.model.dto.business.feedback.FeedbackDisposeRequest;
import com.fanyin.model.vo.feedback.FeedbackVo;
import com.fanyin.model.dto.business.feedback.FeedbackQueryRequest;
import com.github.pagehelper.PageInfo;

/**
 * @author 二哥很猛
 * @date 2019/8/28 10:46
 */
public interface FeedbackService {

    /**
     * 添加反馈信息
     * @param request 前台参数
     */
    void addFeedback(FeedbackAddRequest request);

    /**
     * 分页查询用户反馈列表
     * @param request 查询条件
     * @return 反馈信息 包含用户基本信息
     */
    PageInfo<FeedbackVo> getByPage(FeedbackQueryRequest request);

    /**
     * 反馈信息处理
     * @param request 处理结果信息
     */
    void dispose(FeedbackDisposeRequest request);
}
