package com.eghm.infrastructure.persistence.mybatis.system.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.domain.shared.enums.RoleType;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysRoleMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SysRolePO;
import com.eghm.domain.system.model.SysRole;
import com.eghm.domain.system.repository.SysRoleRepository;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis角色仓储实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysRoleRepository implements SysRoleRepository {

    private final SysRoleMapper sysRoleMapper;

    @Override
    public boolean existsRoleName(String roleName, Long excludeId) {
        LambdaQueryWrapper<SysRolePO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysRolePO::getRoleName, roleName);
        if (excludeId != null) {
            wrapper.ne(SysRolePO::getId, excludeId);
        }
        return sysRoleMapper.selectCount(wrapper) > 0;
    }

    @Override
    public SysRole findById(Long id) {
        return DataUtil.copy(sysRoleMapper.selectById(id), SysRole.class);
    }

    @Override
    public void save(SysRole role) {
        sysRoleMapper.insert(DataUtil.copy(role, SysRolePO.class));
    }

    @Override
    public void update(SysRole role) {
        LambdaUpdateWrapper<SysRolePO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysRolePO::getId, role.getId());
        wrapper.set(SysRolePO::getRoleName, role.getRoleName());
        wrapper.set(SysRolePO::getRemark, role.getRemark());
        sysRoleMapper.update(null, wrapper);
    }

    @Override
    public void logicalDelete(Long id) {
        LambdaUpdateWrapper<SysRolePO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysRolePO::getId, id);
        wrapper.set(SysRolePO::getDeleted, true);
        sysRoleMapper.update(null, wrapper);
    }

    @Override
    public List<SysRole> findCommonRoles() {
        LambdaQueryWrapper<SysRolePO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysRolePO::getRoleType, RoleType.COMMON);
        return DataUtil.copy(sysRoleMapper.selectList(wrapper), SysRole.class);
    }
}
