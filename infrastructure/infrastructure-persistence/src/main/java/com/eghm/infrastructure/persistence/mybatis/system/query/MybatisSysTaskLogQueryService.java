package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.sys.task.TaskLogQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysTaskLogMapper;
import com.eghm.application.system.query.SysTaskLogQueryService;
import com.eghm.application.shared.vo.operate.log.SysTaskLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisSysTaskLogQueryService implements SysTaskLogQueryService {

    private final SysTaskLogMapper sysTaskLogMapper;

    @Override
    public Page<SysTaskLogResponse> getByPage(Page<SysTaskLogResponse> page, TaskLogQueryRequest request) {
        return MybatisPageUtil.fromMybatis(sysTaskLogMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





