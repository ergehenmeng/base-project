package com.eghm.repository.sys;

import com.eghm.mapper.SysUserRoleMapper;
import com.eghm.po.SysUserRolePO;
import com.eghm.sys.repository.SysUserRoleRepository;
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
