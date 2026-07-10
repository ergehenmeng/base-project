package com.eghm.application.operate.service.impl;

import com.eghm.application.shared.dto.ext.SendNotice;
import com.eghm.application.shared.dto.operate.feedback.FeedbackAddDTO;
import com.eghm.application.shared.dto.operate.feedback.FeedbackDisposeRequest;
import com.eghm.domain.shared.enums.MessageType;
import com.eghm.domain.operate.model.FeedbackLog;
import com.eghm.domain.operate.repository.FeedbackLogRepository;
import com.eghm.application.member.service.MemberNoticeApplicationService;
import com.eghm.application.operate.service.FeedbackApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.eghm.application.shared.utils.StringUtil.maxLength;

/**
 * @author 二哥很猛
 * @since 2019/8/28 10:46
 */
@AllArgsConstructor
@Service("feedbackService")
public class FeedbackApplicationServiceImpl implements FeedbackApplicationService {

    private final FeedbackLogRepository feedbackLogRepository;

    private final MemberNoticeApplicationService memberNoticeService;

    @Override
    public void addFeedback(FeedbackAddDTO request) {
        FeedbackLog feedbackLog = DataUtil.copy(request, FeedbackLog.class);
        feedbackLogRepository.save(feedbackLog);
    }

    @Override
    public void dispose(FeedbackDisposeRequest request) {
        FeedbackLog log = feedbackLogRepository.findById(request.getId());
        log.setState(true);
        log.setRemark(request.getRemark());
        feedbackLogRepository.update(log);
        // 发送站内信
        SendNotice notice = new SendNotice();
        notice.setMessageType(MessageType.FEEDBACK_PROCESS);
        Map<String, Object> params = new HashMap<>(4);
        params.put("feedbackType", log.getFeedbackType().getMsg());
        params.put("content", maxLength(log.getContent(), 20));
        params.put("reply", request.getRemark());
        notice.setParams(params);
        memberNoticeService.sendNotice(log.getMemberId(), notice);
    }
}
