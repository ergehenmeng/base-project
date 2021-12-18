package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.eghm.common.enums.FeedbackType;
import com.eghm.common.enums.NoticeType;
import com.eghm.dao.mapper.FeedbackLogMapper;
import com.eghm.dao.model.FeedbackLog;
import com.eghm.model.dto.feedback.FeedbackAddDTO;
import com.eghm.model.dto.feedback.FeedbackDisposeRequest;
import com.eghm.model.dto.feedback.FeedbackQueryRequest;
import com.eghm.model.dto.ext.SendNotice;
import com.eghm.model.vo.feedback.FeedbackVO;
import com.eghm.service.common.FeedbackService;
import com.eghm.service.common.KeyGenerator;
import com.eghm.service.user.UserNoticeService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2019/8/28 10:46
 */
@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

    private FeedbackLogMapper feedbackLogMapper;

    private UserNoticeService userNoticeService;

    private KeyGenerator keyGenerator;

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Autowired
    public void setUserNoticeService(UserNoticeService userNoticeService) {
        this.userNoticeService = userNoticeService;
    }

    @Autowired
    public void setFeedbackLogMapper(FeedbackLogMapper feedbackLogMapper) {
        this.feedbackLogMapper = feedbackLogMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addFeedback(FeedbackAddDTO request) {
        FeedbackLog feedback = DataUtil.copy(request, FeedbackLog.class);
        feedback.setId(keyGenerator.generateKey());
        feedbackLogMapper.insert(feedback);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<FeedbackVO> getByPage(FeedbackQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<FeedbackVO> list = feedbackLogMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
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
