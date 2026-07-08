package com.eghm.sys.repository;

import java.util.List;

/**
 * 用户角色仓储
 *
 * @author 二哥很猛
 */
public interface SysUserRoleRepository {

    List<Long> findRoleIdsByUserId(Long userId);

    void replaceUserRoles(Long userId, List<Long> roleList);
}
