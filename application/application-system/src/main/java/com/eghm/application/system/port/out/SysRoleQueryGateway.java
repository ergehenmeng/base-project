package com.eghm.application.system.port.out;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.vo.sys.ext.SysRoleResponse;

/**
 * 角色查询网关
 *
 * @author 二哥很猛
 */
public interface SysRoleQueryGateway {

    Page<SysRoleResponse> getByPage(PagingQuery request);
}
