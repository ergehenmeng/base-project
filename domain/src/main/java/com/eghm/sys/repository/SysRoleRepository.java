package com.eghm.sys.repository;

import com.eghm.sys.model.SysRole;

import java.util.List;

/**
 * 角色仓储
 *
 * @author 二哥很猛
 */
public interface SysRoleRepository {

    boolean existsRoleName(String roleName, Long excludeId);

    SysRole findById(Long id);

    void save(SysRole role);

    void update(SysRole role);

    void logicalDelete(Long id);

    List<SysRole> findCommonRoles();
}
