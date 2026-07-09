package com.eghm.domain.operate.repository;

import com.eghm.domain.operate.model.FeedbackLog;

/**
 * 用户反馈仓储接口
 *
 * @author 二哥很猛
 * @since 2019/8/28 10:46
 */
public interface FeedbackLogRepository {

    /**
     * 根据id查询反馈
     *
     * @param id id
     * @return 反馈信息
     */
    FeedbackLog findById(Long id);

    /**
     * 添加反馈信息
     *
     * @param feedbackLog 反馈信息
     */
    void save(FeedbackLog feedbackLog);

    /**
     * 更新反馈信息
     *
     * @param feedbackLog 反馈信息
     */
    void update(FeedbackLog feedbackLog);
}
