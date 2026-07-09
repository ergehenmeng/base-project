package com.eghm.infrastructure.persistence.mybatis.system.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.SmsLogMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SmsLogPO;
import com.eghm.domain.system.model.SmsLog;
import com.eghm.domain.system.repository.SmsLogRepository;
import com.eghm.application.shared.utils.DataUtil;
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
