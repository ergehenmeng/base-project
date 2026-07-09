package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.operate.feedback.FeedbackQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.FeedbackLogMapper;
import com.eghm.application.operate.query.FeedbackQueryService;
import com.eghm.application.shared.vo.operate.feedback.FeedbackResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisFeedbackQueryService implements FeedbackQueryService {

    private final FeedbackLogMapper feedbackLogMapper;

    @Override
    public Page<FeedbackResponse> getByPage(Page<FeedbackResponse> page, FeedbackQueryRequest request) {
        return MybatisPageUtil.fromMybatis(feedbackLogMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





