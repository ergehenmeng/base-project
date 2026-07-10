package com.eghm.application.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.vo.sys.ext.SysRoleResponse;

/**
 * 角色查询服务
 *
 * @author 二哥很猛
 */
public interface SysRoleQueryService {

    Page<SysRoleResponse> getByPage(PagingQuery request);
}
