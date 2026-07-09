package com.eghm.infrastructure.persistence.mybatis.system.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.SysTaskLogMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SysTaskLogPO;
import com.eghm.domain.system.model.SysTaskLog;
import com.eghm.domain.system.repository.SysTaskLogRepository;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisSysTaskLogRepository implements SysTaskLogRepository {

    private final SysTaskLogMapper sysTaskLogMapper;

    @Override
    public void save(SysTaskLog log) {
        sysTaskLogMapper.insert(DataUtil.copy(log, SysTaskLogPO.class));
    }

    @Override
    public String getErrorMsg(Long id) {
        return sysTaskLogMapper.getErrorMsg(id);
    }
}
