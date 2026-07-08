package com.eghm.query.sys;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.sys.config.ConfigQueryRequest;
import com.eghm.mapper.SysConfigMapper;
import com.eghm.service.sys.SysConfigQueryGateway;
import com.eghm.vo.sys.ext.SysConfigResponse;
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

