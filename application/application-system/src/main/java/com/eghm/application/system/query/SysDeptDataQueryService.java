package com.eghm.application.system.query;

import java.util.List;

/**
 * 部门数据权限查询网关
 *
 * @author eghm
 */
public interface SysDeptDataQueryService {

    /**
     * 获取用户所拥有的所有部门(数据权限)
     *
     * @param userId 用户id
     * @return 部门id
     */
    List<String> getDeptList(Long userId);
}
