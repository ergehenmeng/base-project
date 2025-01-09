package com.eghm.service.operate.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.SendNotice;
import com.eghm.dto.operate.feedback.FeedbackAddDTO;
import com.eghm.dto.operate.feedback.FeedbackDisposeRequest;
import com.eghm.dto.operate.feedback.FeedbackQueryRequest;
import com.eghm.enums.MessageType;
import com.eghm.mapper.FeedbackLogMapper;
import com.eghm.model.FeedbackLog;
import com.eghm.service.business.MemberNoticeService;
import com.eghm.service.operate.FeedbackService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.operate.feedback.FeedbackResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
        notice.setMessageType(MessageType.FEEDBACK_PROCESS);
        Map<String, Object> params = new HashMap<>();
        params.put("feedbackType", log.getFeedbackType().getMsg());
        params.put("content", StrUtil.maxLength(log.getContent(), 20));
        params.put("reply", request.getRemark());
        notice.setParams(params);
        memberNoticeService.sendNotice(log.getMemberId(), notice);
    }
}
