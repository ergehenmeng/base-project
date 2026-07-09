package com.eghm.infrastructure.persistence.mybatis.system.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.SysUserRoleMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SysUserRolePO;
import com.eghm.domain.system.repository.SysUserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis用户角色仓储实现
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisSysUserRoleRepository implements SysUserRoleRepository {

    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<Long> findRoleIdsByUserId(Long userId) {
        return sysUserRoleMapper.getByUserId(userId);
    }

    @Override
    public void replaceUserRoles(Long userId, List<Long> roleList) {
        sysUserRoleMapper.deleteByUserId(userId);
        roleList.forEach(roleId -> sysUserRoleMapper.insert(new SysUserRolePO(userId, roleId)));
    }
}
