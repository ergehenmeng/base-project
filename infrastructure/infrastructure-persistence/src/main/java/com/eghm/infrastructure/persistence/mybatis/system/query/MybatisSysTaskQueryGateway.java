package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.dto.sys.task.TaskQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysTaskMapper;
import com.eghm.application.system.service.SysTaskQueryGateway;
import com.eghm.vo.operate.task.SysTaskResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis定时任务查询网关实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysTaskQueryGateway implements SysTaskQueryGateway {

    private final SysTaskMapper sysTaskMapper;

    @Override
    public Page<SysTaskResponse> getByPage(TaskQueryRequest request) {
        return MybatisPageUtil.fromMybatis(sysTaskMapper.getByPage(MybatisPageUtil.toMybatis(request.createPage()), request));
    }
}

