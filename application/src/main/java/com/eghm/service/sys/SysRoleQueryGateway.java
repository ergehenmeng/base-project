package com.eghm.service.sys;

import com.eghm.dto.ext.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.vo.sys.ext.SysRoleResponse;

/**
 * 角色查询网关
 *
 * @author 二哥很猛
 */
public interface SysRoleQueryGateway {

    Page<SysRoleResponse> getByPage(PagingQuery request);
}
