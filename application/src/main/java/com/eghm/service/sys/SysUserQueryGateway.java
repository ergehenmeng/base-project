package com.eghm.service.sys;

import com.eghm.dto.ext.Page;
import com.eghm.dto.sys.user.UserQueryRequest;
import com.eghm.vo.sys.user.UserResponse;

/**
 * 系统用户查询网关
 *
 * @author 二哥很猛
 */
public interface SysUserQueryGateway {

    Page<UserResponse> getByPage(UserQueryRequest request);
}
