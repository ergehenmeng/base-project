package com.eghm.application.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.dto.ext.Page;
import com.eghm.configuration.authentication.SecurityHolder;
import com.eghm.dto.ext.CheckBox;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.sys.role.RoleAddRequest;
import com.eghm.dto.sys.role.RoleEditRequest;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.UserType;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.application.system.service.SysRoleQueryGateway;
import com.eghm.application.system.service.SysRoleService;
import com.eghm.domain.system.model.SysRole;
import com.eghm.domain.system.repository.SysRoleMenuRepository;
import com.eghm.domain.system.repository.SysRoleRepository;
import com.eghm.domain.system.repository.SysUserRoleRepository;
import com.eghm.utils.DataUtil;
import com.eghm.vo.sys.ext.SysRoleResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/11/26 15:33
 */
@Slf4j
@AllArgsConstructor
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleRepository sysRoleRepository;

    private final SysUserRoleRepository sysUserRoleRepository;

    private final SysRoleMenuRepository sysRoleMenuRepository;

    private final SysRoleQueryGateway sysRoleQueryGateway;

    @Override
    public Page<SysRoleResponse> getByPage(PagingQuery request) {
        return sysRoleQueryGateway.getByPage(request);
    }

    @Override
    public void update(RoleEditRequest request) {
        if (sysRoleRepository.existsRoleName(request.getRoleName(), request.getId())) {
            log.warn("角色名称重复 [{}] [{}]", request.getId(), request.getRoleName());
            throw new BusinessException(ErrorCode.ROLE_NAME_REDO);
        }
        sysRoleRepository.update(DataUtil.copy(request, SysRole.class));
    }

    @Override
    public void delete(Long id) {
        SysRole role = sysRoleRepository.findById(id);
        if (role == null) {
            return;
        }
        role.assertDeletable();
        sysRoleRepository.logicalDelete(id);
    }

    @Override
    public void create(RoleAddRequest request) {
        if (sysRoleRepository.existsRoleName(request.getRoleName(), null)) {
            log.warn("角色名称重复 [{}]", request.getRoleName());
            throw new BusinessException(ErrorCode.ROLE_NAME_REDO);
        }
        sysRoleRepository.save(DataUtil.copy(request, SysRole.class));
    }

    @Override
    public List<CheckBox> getList() {
        List<SysRole> roleList = sysRoleRepository.findCommonRoles();
        return DataUtil.copy(roleList, role -> new CheckBox(role.getId(), role.getRoleName()));
    }

    @Override
    public List<Long> getByUserId(Long userId) {
        return sysUserRoleRepository.findRoleIdsByUserId(userId);
    }

    @Override
    public List<String> getRoleMenu(Long roleId) {
        return sysRoleMenuRepository.findMenuIdsByRoleId(roleId);
    }

    @Override
    public void authMenu(Long roleId, List<Long> menuIds) {
        UserType userType = SecurityHolder.getUserType();
        if (userType != UserType.ADMINISTRATOR) {
            log.warn("为保证系统安全性,非管理员将无法进行菜单授权操作 [{}]", SecurityHolder.getUserId());
            throw new BusinessException(ErrorCode.ADMIN_AUTH);
        }
        sysRoleMenuRepository.replaceRoleMenus(roleId, CollUtil.emptyIfNull(menuIds));
    }

    @Override
    public void auth(Long userId, List<Long> roleList) {
        sysUserRoleRepository.replaceUserRoles(userId, roleList);
    }

    @Override
    public SysRole getById(Long id) {
        return sysRoleRepository.findById(id);
    }
}
