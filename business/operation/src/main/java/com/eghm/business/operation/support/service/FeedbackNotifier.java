package com.eghm.business.operation.support.service;

import com.eghm.integration.messaging.dto.SendNotice;

/**
 * 反馈处理结果通知接口。
 */
public interface FeedbackNotifier {

    /**
     * 向会员发送反馈处理结果。
     *
     * @param memberId 会员主键
     * @param notice   通知内容
     */
    void sendNotice(Long memberId, SendNotice notice);
}
