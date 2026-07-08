package com.eghm.repository.sys;

import com.eghm.mapper.SysTaskLogMapper;
import com.eghm.po.SysTaskLogPO;
import com.eghm.sys.model.SysTaskLog;
import com.eghm.sys.repository.SysTaskLogRepository;
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
