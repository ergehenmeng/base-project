package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.sys.config.ConfigQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysConfigMapper;
import com.eghm.application.system.port.out.SysConfigQueryGateway;
import com.eghm.application.shared.vo.sys.ext.SysConfigResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis系统配置查询网关实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysConfigQueryGateway implements SysConfigQueryGateway {

    private final SysConfigMapper sysConfigMapper;

    @Override
    public Page<SysConfigResponse> getByPage(ConfigQueryRequest request) {
        return MybatisPageUtil.fromMybatis(sysConfigMapper.getByPage(MybatisPageUtil.toMybatis(request.createPage()), request));
    }
}

