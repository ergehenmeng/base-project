package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.FeedbackType;
import com.eghm.common.enums.NoticeType;
import com.eghm.dao.mapper.FeedbackLogMapper;
import com.eghm.dao.model.FeedbackLog;
import com.eghm.model.dto.ext.SendNotice;
import com.eghm.model.dto.feedback.FeedbackAddDTO;
import com.eghm.model.dto.feedback.FeedbackDisposeRequest;
import com.eghm.model.dto.feedback.FeedbackQueryRequest;
import com.eghm.model.vo.feedback.FeedbackVO;
import com.eghm.service.common.FeedbackService;
import com.eghm.service.user.UserNoticeService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2019/8/28 10:46
 */
@Service("feedbackService")
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackLogMapper feedbackLogMapper;

    private final UserNoticeService userNoticeService;

    @Override
    public void addFeedback(FeedbackAddDTO request) {
        FeedbackLog feedback = DataUtil.copy(request, FeedbackLog.class);
        feedbackLogMapper.insert(feedback);
    }

    @Override
    public Page<FeedbackVO> getByPage(FeedbackQueryRequest request) {
        return feedbackLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void dispose(FeedbackDisposeRequest request) {
        FeedbackLog log = feedbackLogMapper.selectById(request.getId());
        log.setState((byte) 1);
        feedbackLogMapper.updateById(log);
        SendNotice notice = new SendNotice();
        notice.setNoticeType(NoticeType.FEEDBACK_PROCESS);
        Map<String, Object> params = new HashMap<>();
        params.put("classify", FeedbackType.getType(log.getClassify()).getMsg());
        params.put("content", StrUtil.maxLength(log.getContent(), 20));
        notice.setParams(params);
        userNoticeService.sendNotice(log.getUserId(), notice);
    }
}
