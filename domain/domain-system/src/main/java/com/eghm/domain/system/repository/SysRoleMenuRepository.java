package com.eghm.domain.system.repository;

import java.util.List;

/**
 * 角色菜单仓储
 *
 * @author 二哥很猛
 */
public interface SysRoleMenuRepository {

    List<String> findMenuIdsByRoleId(Long roleId);

    void replaceRoleMenus(Long roleId, List<Long> menuIds);
}
