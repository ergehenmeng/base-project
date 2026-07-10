package com.eghm.application.system.service;

import cn.hutool.core.collection.CollUtil;
import com.eghm.application.shared.configuration.authentication.SecurityHolder;
import com.eghm.application.shared.dto.sys.role.RoleAddRequest;
import com.eghm.application.shared.dto.sys.role.RoleEditRequest;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.UserType;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.system.model.SysRole;
import com.eghm.domain.system.repository.SysRoleMenuRepository;
import com.eghm.domain.system.repository.SysRoleRepository;
import com.eghm.domain.system.repository.SysUserRoleRepository;
import com.eghm.domain.system.service.SysRoleDomainService;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/11/26 15:33
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysRoleApplicationService {

    private final SysRoleRepository sysRoleRepository;

    private final SysUserRoleRepository sysUserRoleRepository;

    private final SysRoleMenuRepository sysRoleMenuRepository;

    private static final SysRoleDomainService SYS_ROLE_DOMAIN_SERVICE = new SysRoleDomainService();

    /**
     * 更新角色信息
     *
     * @param request 前台参数
     */
    public void update(RoleEditRequest request) {
        SYS_ROLE_DOMAIN_SERVICE.assertRoleNameAvailable(sysRoleRepository, request.getRoleName(), request.getId());
        sysRoleRepository.update(DataUtil.copy(request, SysRole.class));
    }

    /**
     * 删除角色信息
     *
     * @param id 主键
     */
    public void delete(Long id) {
        SysRole role = SYS_ROLE_DOMAIN_SERVICE.getDeletableRole(sysRoleRepository, id);
        if (role == null) {
            return;
        }
        sysRoleRepository.logicalDelete(id);
    }

    /**
     * 添加角色信息
     *
     * @param request 前台参数
     */
    public void create(RoleAddRequest request) {
        SYS_ROLE_DOMAIN_SERVICE.assertRoleNameAvailable(sysRoleRepository, request.getRoleName(), null);
        sysRoleRepository.save(DataUtil.copy(request, SysRole.class));
    }

    /**
     * 角色菜单关联关系保存
     *
     * @param roleId  角色id
     * @param menuIds 菜单ids
     */
    public void authMenu(Long roleId, List<Long> menuIds) {
        UserType userType = SecurityHolder.getUserType();
        if (userType != UserType.ADMINISTRATOR) {
            log.warn("为保证系统安全性,非管理员将无法进行菜单授权操作 [{}]", SecurityHolder.getUserId());
            throw new BusinessException(ErrorCode.ADMIN_AUTH);
        }
        sysRoleMenuRepository.replaceRoleMenus(roleId, CollUtil.emptyIfNull(menuIds));
    }

    /**
     * 商户角色授权 (角色id)
     *
     * @param userId   商户对应于用户id
     * @param roleList 角色id
     */
    public void auth(Long userId, List<Long> roleList) {
        sysUserRoleRepository.replaceUserRoles(userId, roleList);
    }
}
