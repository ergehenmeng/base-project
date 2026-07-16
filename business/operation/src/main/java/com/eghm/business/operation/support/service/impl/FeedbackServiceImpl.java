package com.eghm.business.operation.support.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.integration.messaging.dto.SendNotice;
import com.eghm.business.operation.support.dto.FeedbackAddDTO;
import com.eghm.business.operation.support.dto.FeedbackDisposeRequest;
import com.eghm.business.operation.support.dto.FeedbackQueryRequest;
import com.eghm.foundation.core.enums.MessageType;
import com.eghm.business.operation.support.mapper.FeedbackLogMapper;
import com.eghm.business.operation.support.entity.FeedbackLog;
import com.eghm.business.operation.support.service.FeedbackNotifier;
import com.eghm.business.operation.support.service.FeedbackService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.business.operation.support.vo.FeedbackResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.eghm.foundation.core.utils.StringUtil.maxLength;

/**
 * @author 二哥很猛
 * @since 2019/8/28 10:46
 */
@AllArgsConstructor
@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackLogMapper feedbackLogMapper;

    private final FeedbackNotifier feedbackNotifier;

    @Override
    public Page<FeedbackResponse> getByPage(FeedbackQueryRequest request) {
        return feedbackLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void addFeedback(FeedbackAddDTO request) {
        DataUtil.copy(request, FeedbackLog.class, feedbackLogMapper::insert);
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
        Map<String, Object> params = new HashMap<>(4);
        params.put("feedbackType", log.getFeedbackType().getMsg());
        params.put("content", maxLength(log.getContent(), 20));
        params.put("reply", request.getRemark());
        notice.setParams(params);
        feedbackNotifier.sendNotice(log.getMemberId(), notice);
    }
}
