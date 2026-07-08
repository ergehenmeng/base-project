package com.eghm.repository.pay;

import com.eghm.mapper.PayNotifyLogMapper;
import com.eghm.pay.model.PayNotifyLog;
import com.eghm.pay.repository.PayNotifyLogRepository;
import com.eghm.po.PayNotifyLogPO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisPayNotifyLogRepository implements PayNotifyLogRepository {

    private final PayNotifyLogMapper payNotifyLogMapper;

    @Override
    public void save(PayNotifyLog log) {
        payNotifyLogMapper.insert(DataUtil.copy(log, PayNotifyLogPO.class));
    }

    @Override
    public PayNotifyLog findById(Long id) {
        return DataUtil.copy(payNotifyLogMapper.selectById(id), PayNotifyLog.class);
    }

    @Override
    public void markPlaybackSuccess(Long id) {
        PayNotifyLog log = new PayNotifyLog();
        log.setId(id);
        log.setState(1);
        payNotifyLogMapper.updateById(DataUtil.copy(log, PayNotifyLogPO.class));
    }
}
