package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.dto.operate.feedback.FeedbackQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.FeedbackLogMapper;
import com.eghm.application.operate.service.FeedbackQueryGateway;
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





