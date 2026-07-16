package com.eghm.member.engagement.service.impl;

import com.eghm.business.operation.support.service.FeedbackNotifier;
import com.eghm.integration.messaging.dto.SendNotice;
import com.eghm.member.engagement.service.MemberNoticeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 使用会员站内信发送反馈处理结果。
 */
@Service
@AllArgsConstructor
public class MemberFeedbackNotifier implements FeedbackNotifier {

    private final MemberNoticeService memberNoticeService;

    @Override
    public void sendNotice(Long memberId, SendNotice notice) {
        memberNoticeService.sendNotice(memberId, notice);
    }
}
