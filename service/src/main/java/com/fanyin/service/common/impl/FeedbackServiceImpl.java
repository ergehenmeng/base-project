package com.fanyin.service.common.impl;

import com.fanyin.dao.mapper.business.FeedbackLogMapper;
import com.fanyin.dao.model.business.FeedbackLog;
import com.fanyin.model.dto.business.feedback.FeedbackAddRequest;
import com.fanyin.model.dto.business.feedback.FeedbackDisposeRequest;
import com.fanyin.model.dto.business.feedback.FeedbackModel;
import com.fanyin.model.dto.business.feedback.FeedbackQueryRequest;
import com.fanyin.service.common.FeedbackService;
import com.fanyin.utils.DataUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/28 10:46
 */
@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackLogMapper feedbackLogMapper;

    @Override
    public void addFeedback(FeedbackAddRequest request) {
        FeedbackLog feedback = DataUtil.copy(request, FeedbackLog.class);
        feedbackLogMapper.insertSelective(feedback);
    }

    @Override
    public PageInfo<FeedbackModel> getByPage(FeedbackQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getPageSize());
        List<FeedbackModel> list = feedbackLogMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public void dispose(FeedbackDisposeRequest request) {
        FeedbackLog feedbackLog = DataUtil.copy(request, FeedbackLog.class);
        feedbackLog.setState((byte)1);
        feedbackLogMapper.updateByPrimaryKeySelective(feedbackLog);
        //TODO 可发送站内信
    }
}
