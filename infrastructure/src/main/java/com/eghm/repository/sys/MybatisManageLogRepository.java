package com.eghm.repository.sys;

import com.eghm.mapper.ManageLogMapper;
import com.eghm.po.ManageLogPO;
import com.eghm.sys.model.ManageLog;
import com.eghm.sys.repository.ManageLogRepository;
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
