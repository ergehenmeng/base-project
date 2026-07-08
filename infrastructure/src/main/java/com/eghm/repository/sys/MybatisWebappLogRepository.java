package com.eghm.repository.sys;

import com.eghm.mapper.WebappLogMapper;
import com.eghm.po.WebappLogPO;
import com.eghm.sys.model.WebappLog;
import com.eghm.sys.repository.WebappLogRepository;
import com.eghm.utils.DataUtil;
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
