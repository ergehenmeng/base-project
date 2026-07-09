package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysRoleMapper;
import com.eghm.application.system.query.SysRoleQueryService;
import com.eghm.application.shared.vo.sys.ext.SysRoleResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis角色查询网关实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysRoleQueryService implements SysRoleQueryService {

    private final SysRoleMapper sysRoleMapper;

    @Override
    public Page<SysRoleResponse> getByPage(PagingQuery request) {
        return MybatisPageUtil.fromMybatis(sysRoleMapper.getByPage(MybatisPageUtil.toMybatis(request.createPage()), request.getQueryName()));
    }
}

