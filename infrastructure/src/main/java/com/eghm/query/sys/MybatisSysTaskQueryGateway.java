package com.eghm.query.sys;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.sys.task.TaskQueryRequest;
import com.eghm.mapper.SysTaskMapper;
import com.eghm.service.sys.SysTaskQueryGateway;
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

