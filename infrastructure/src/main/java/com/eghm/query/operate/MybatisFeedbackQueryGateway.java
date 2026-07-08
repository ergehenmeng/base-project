package com.eghm.query.operate;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.operate.feedback.FeedbackQueryRequest;
import com.eghm.mapper.FeedbackLogMapper;
import com.eghm.service.operate.FeedbackQueryGateway;
import com.eghm.vo.operate.feedback.FeedbackResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisFeedbackQueryGateway implements FeedbackQueryGateway {

    private final FeedbackLogMapper feedbackLogMapper;

    @Override
    public Page<FeedbackResponse> getByPage(Page<FeedbackResponse> page, FeedbackQueryRequest request) {
        return MybatisPageUtil.fromMybatis(feedbackLogMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





