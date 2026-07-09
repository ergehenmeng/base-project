package com.eghm.infrastructure.persistence.mybatis.payment.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.PayRequestLogMapper;
import com.eghm.domain.payment.model.PayRequestLog;
import com.eghm.domain.payment.repository.PayRequestLogRepository;
import com.eghm.infrastructure.persistence.mybatis.po.PayRequestLogPO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisPayRequestLogRepository implements PayRequestLogRepository {

    private final PayRequestLogMapper payRequestLogMapper;

    @Override
    public void save(PayRequestLog requestLog) {
        payRequestLogMapper.insert(DataUtil.copy(requestLog, PayRequestLogPO.class));
    }
}
