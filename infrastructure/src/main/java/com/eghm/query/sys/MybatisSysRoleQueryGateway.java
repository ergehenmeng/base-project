package com.eghm.query.sys;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.mapper.SysRoleMapper;
import com.eghm.service.sys.SysRoleQueryGateway;
import com.eghm.vo.sys.ext.SysRoleResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis角色查询网关实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysRoleQueryGateway implements SysRoleQueryGateway {

    private final SysRoleMapper sysRoleMapper;

    @Override
    public Page<SysRoleResponse> getByPage(PagingQuery request) {
        return MybatisPageUtil.fromMybatis(sysRoleMapper.getByPage(MybatisPageUtil.toMybatis(request.createPage()), request.getQueryName()));
    }
}

