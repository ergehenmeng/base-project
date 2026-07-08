package com.eghm.query.sys;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.sys.task.TaskLogQueryRequest;
import com.eghm.mapper.SysTaskLogMapper;
import com.eghm.service.sys.SysTaskLogQueryGateway;
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





