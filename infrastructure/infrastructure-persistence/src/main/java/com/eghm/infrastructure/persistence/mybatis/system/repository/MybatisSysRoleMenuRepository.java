package com.eghm.infrastructure.persistence.mybatis.system.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.SysRoleMapper;
import com.eghm.domain.system.repository.SysRoleMenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis角色菜单仓储实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysRoleMenuRepository implements SysRoleMenuRepository {

    private final SysRoleMapper sysRoleMapper;

    @Override
    public List<String> findMenuIdsByRoleId(Long roleId) {
        return sysRoleMapper.getRoleMenu(roleId);
    }

    @Override
    public void replaceRoleMenus(Long roleId, List<Long> menuIds) {
        sysRoleMapper.deleteRoleMenu(roleId);
        if (!menuIds.isEmpty()) {
            sysRoleMapper.batchInsert(roleId, menuIds);
        }
    }
}
