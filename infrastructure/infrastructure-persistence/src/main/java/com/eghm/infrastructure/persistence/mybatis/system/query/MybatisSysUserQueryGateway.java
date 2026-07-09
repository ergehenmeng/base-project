package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.sys.user.UserQueryRequest;
import com.eghm.domain.shared.enums.UserType;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysUserMapper;
import com.eghm.application.system.port.out.SysUserQueryGateway;
import com.eghm.application.shared.vo.sys.user.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis系统用户查询网关实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysUserQueryGateway implements SysUserQueryGateway {

    private final SysUserMapper sysUserMapper;

    @Override
    public Page<UserResponse> getByPage(UserQueryRequest request) {
        request.setUserType(UserType.SYS_USER.getValue());
        return MybatisPageUtil.fromMybatis(sysUserMapper.listPage(MybatisPageUtil.toMybatis(request.createPage()), request));
    }
}

