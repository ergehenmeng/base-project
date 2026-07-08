package com.eghm.query.sys;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.sys.user.UserQueryRequest;
import com.eghm.enums.UserType;
import com.eghm.mapper.SysUserMapper;
import com.eghm.service.sys.SysUserQueryGateway;
import com.eghm.vo.sys.user.UserResponse;
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

