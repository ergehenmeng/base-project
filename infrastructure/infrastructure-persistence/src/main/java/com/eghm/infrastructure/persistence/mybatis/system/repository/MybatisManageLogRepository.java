package com.eghm.infrastructure.persistence.mybatis.system.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.ManageLogMapper;
import com.eghm.infrastructure.persistence.mybatis.po.ManageLogPO;
import com.eghm.domain.system.model.ManageLog;
import com.eghm.domain.system.repository.ManageLogRepository;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisManageLogRepository implements ManageLogRepository {

    private final ManageLogMapper manageLogMapper;

    @Override
    public void save(ManageLog log) {
        manageLogMapper.insert(DataUtil.copy(log, ManageLogPO.class));
    }
}
