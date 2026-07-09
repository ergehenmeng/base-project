package com.eghm.application.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.user.UserQueryRequest;
import com.eghm.application.shared.vo.sys.user.UserResponse;

/**
 * 系统用户查询网关
 *
 * @author 二哥很猛
 */
public interface SysUserQueryService {

    Page<UserResponse> getByPage(UserQueryRequest request);
}
