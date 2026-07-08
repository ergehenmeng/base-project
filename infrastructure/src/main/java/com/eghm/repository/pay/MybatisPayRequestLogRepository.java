package com.eghm.repository.pay;

import com.eghm.mapper.PayRequestLogMapper;
import com.eghm.pay.model.PayRequestLog;
import com.eghm.pay.repository.PayRequestLogRepository;
import com.eghm.po.PayRequestLogPO;
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
