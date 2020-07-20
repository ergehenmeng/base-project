package com.eghm.service.common.impl;

import com.eghm.dao.mapper.business.FeedbackLogMapper;
import com.eghm.dao.model.business.FeedbackLog;
import com.eghm.model.dto.business.feedback.FeedbackAddRequest;
import com.eghm.model.dto.business.feedback.FeedbackDisposeRequest;
import com.eghm.model.dto.business.feedback.FeedbackQueryRequest;
import com.eghm.model.vo.feedback.FeedbackVO;
import com.eghm.service.common.FeedbackService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/28 10:46
 */
@Service("feedbackService")
@Transactional(rollbackFor = RuntimeException.class)
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackLogMapper feedbackLogMapper;

    @Override
    public void addFeedback(FeedbackAddRequest request) {
        FeedbackLog feedback = DataUtil.copy(request, FeedbackLog.class);
        feedbackLogMapper.insertSelective(feedback);
    }

    @Override
    public PageInfo<FeedbackVO> getByPage(FeedbackQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<FeedbackVO> list = feedbackLogMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public void dispose(FeedbackDisposeRequest request) {
        FeedbackLog feedbackLog = DataUtil.copy(request, FeedbackLog.class);
        feedbackLog.setState((byte) 1);
        feedbackLogMapper.updateByPrimaryKeySelective(feedbackLog);
        //TODO 可发送站内信
    }
}
