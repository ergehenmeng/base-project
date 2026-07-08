package com.eghm.repository.operate;

import com.eghm.mapper.FeedbackLogMapper;
import com.eghm.operate.model.FeedbackLog;
import com.eghm.operate.repository.FeedbackLogRepository;
import com.eghm.po.FeedbackLogPO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisFeedbackLogRepository implements FeedbackLogRepository {

    private final FeedbackLogMapper feedbackLogMapper;

    @Override
    public FeedbackLog findById(Long id) {
        return DataUtil.copy(feedbackLogMapper.selectById(id), FeedbackLog.class);
    }

    @Override
    public void save(FeedbackLog feedbackLog) {
        feedbackLogMapper.insert(DataUtil.copy(feedbackLog, FeedbackLogPO.class));
    }

    @Override
    public void update(FeedbackLog feedbackLog) {
        feedbackLogMapper.updateById(DataUtil.copy(feedbackLog, FeedbackLogPO.class));
    }
}
