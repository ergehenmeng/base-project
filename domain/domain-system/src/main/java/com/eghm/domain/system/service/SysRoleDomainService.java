package com.eghm.domain.system.service;

import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.system.model.SysRole;
import com.eghm.domain.system.repository.SysRoleRepository;

/**
 * 角色领域服务.
 *
 * @author 二哥很猛
 */
public class SysRoleDomainService {

    public void assertRoleNameAvailable(SysRoleRepository repository, String roleName, Long excludeId) {
        if (repository.existsRoleName(roleName, excludeId)) {
            throw new BusinessException(ErrorCode.ROLE_NAME_REDO);
        }
    }

    public SysRole getDeletableRole(SysRoleRepository repository, Long id) {
        SysRole role = repository.findById(id);
        if (role != null) {
            role.assertDeletable();
        }
        return role;
    }
}
