package com.eghm.infrastructure.persistence.mybatis.system.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.WebappLogMapper;
import com.eghm.infrastructure.persistence.mybatis.po.WebappLogPO;
import com.eghm.domain.system.model.WebappLog;
import com.eghm.domain.system.repository.WebappLogRepository;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisWebappLogRepository implements WebappLogRepository {

    private final WebappLogMapper webappLogMapper;

    @Override
    public void save(WebappLog log) {
        webappLogMapper.insert(DataUtil.copy(log, WebappLogPO.class));
    }
}
