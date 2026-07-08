package com.eghm.repository.sys;

import com.eghm.mapper.SmsLogMapper;
import com.eghm.po.SmsLogPO;
import com.eghm.sys.model.SmsLog;
import com.eghm.sys.repository.SmsLogRepository;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisSmsLogRepository implements SmsLogRepository {

    private final SmsLogMapper smsLogMapper;

    @Override
    public void save(SmsLog smsLog) {
        smsLogMapper.insert(DataUtil.copy(smsLog, SmsLogPO.class));
    }
}
