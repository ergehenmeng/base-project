package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.dto.sys.task.TaskLogQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysTaskLogMapper;
import com.eghm.application.system.service.SysTaskLogQueryGateway;
import com.eghm.vo.operate.log.SysTaskLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisSysTaskLogQueryGateway implements SysTaskLogQueryGateway {

    private final SysTaskLogMapper sysTaskLogMapper;

    @Override
    public Page<SysTaskLogResponse> getByPage(Page<SysTaskLogResponse> page, TaskLogQueryRequest request) {
        return MybatisPageUtil.fromMybatis(sysTaskLogMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





