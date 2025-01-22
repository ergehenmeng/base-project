package com.eghm.service.common.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.SendNotice;
import com.eghm.dto.operate.feedback.FeedbackAddDTO;
import com.eghm.dto.operate.feedback.FeedbackDisposeRequest;
import com.eghm.dto.operate.feedback.FeedbackQueryRequest;
import com.eghm.enums.FeedbackType;
import com.eghm.enums.NoticeType;
import com.eghm.mapper.FeedbackLogMapper;
import com.eghm.model.FeedbackLog;
import com.eghm.service.common.FeedbackService;
import com.eghm.service.member.MemberNoticeService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.feedback.FeedbackResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.eghm.utils.StringUtil.maxLength;

/**
 * @author 二哥很猛
 * @since 2019/8/28 10:46
 */
@Service("feedbackService")
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackLogMapper feedbackLogMapper;

    private final MemberNoticeService memberNoticeService;

    @Override
    public Page<FeedbackResponse> getByPage(FeedbackQueryRequest request) {
        return feedbackLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void addFeedback(FeedbackAddDTO request) {
        FeedbackLog feedback = DataUtil.copy(request, FeedbackLog.class);
        feedbackLogMapper.insert(feedback);
    }

    @Override
    public void dispose(FeedbackDisposeRequest request) {
        FeedbackLog log = feedbackLogMapper.selectById(request.getId());
        log.setState(true);
        log.setRemark(request.getRemark());
        feedbackLogMapper.updateById(log);
        // 发送站内信
        SendNotice notice = new SendNotice();
        notice.setNoticeType(NoticeType.FEEDBACK_PROCESS);
        Map<String, Object> params = new HashMap<>(4);
        params.put("feedbackType", FeedbackType.of(log.getFeedbackType()).getMsg());
        params.put("content", maxLength(log.getContent(), 20));
        params.put("reply", request.getRemark());
        notice.setParams(params);
        memberNoticeService.sendNotice(log.getMemberId(), notice);
    }
}
