package com.eghm.platform.iam.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.foundation.core.configuration.authentication.SecurityHolder;
import com.eghm.foundation.core.dto.ext.CheckBox;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.platform.iam.dto.RoleAddRequest;
import com.eghm.platform.iam.dto.RoleEditRequest;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.enums.RoleType;
import com.eghm.foundation.core.enums.UserType;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.platform.iam.mapper.SysRoleMapper;
import com.eghm.platform.iam.mapper.SysUserRoleMapper;
import com.eghm.platform.iam.entity.SysRole;
import com.eghm.platform.iam.entity.SysUserRole;
import com.eghm.platform.iam.service.SysRoleService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.foundation.web.utility.MybatisUtil;
import com.eghm.foundation.web.utility.ValidationUtil;
import com.eghm.platform.iam.vo.SysRoleResponse;
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

    private final SysRoleMapper sysRoleMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    public Page<SysRoleResponse> getByPage(PagingQuery request) {
        return sysRoleMapper.getByPage(request.createPage(), request.getQueryName());
    }

    @Override
    public void update(RoleEditRequest request) {
        ValidationUtil.redoCheck(sysRoleMapper, SysRole::getRoleName, request.getRoleName(), SysRole::getId, request.getId(), ErrorCode.ROLE_NAME_REDO, "角色名称重复 [{}] [{}]");
        LambdaUpdateWrapper<SysRole> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysRole::getId, request.getId());
        wrapper.set(SysRole::getRoleName, request.getRoleName());
        wrapper.set(SysRole::getRemark, request.getRemark());
        sysRoleMapper.update(null, wrapper);
    }

    @Override
    public void delete(Long id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        if (sysRole == null) {
            return;
        }
        if (sysRole.getRoleType() != RoleType.COMMON) {
            log.info("该角色为系统默认角色,无法删除 [{}] [{}]", id, sysRole.getRoleType());
            throw new BusinessException(ErrorCode.ROLE_FORBID_DELETE);
        }
        LambdaUpdateWrapper<SysRole> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysRole::getId, id);
        wrapper.set(SysRole::getDeleted, true);
        sysRoleMapper.update(null, wrapper);
    }

    @Override
    public void create(RoleAddRequest request) {
        ValidationUtil.redoCheck(sysRoleMapper, SysRole::getRoleName, request.getRoleName(), null, null, ErrorCode.ROLE_NAME_REDO, "角色名称重复 [{}] [{}]");
        DataUtil.copy(request, SysRole.class, sysRoleMapper::insert);
    }

    @Override
    public List<CheckBox> getList() {
        List<SysRole> roleList = MybatisUtil.getList(sysRoleMapper, SysRole::getRoleType, RoleType.COMMON);
        return DataUtil.copy(roleList, sysRole -> new CheckBox(sysRole.getId(), sysRole.getRoleName()));
    }

    @Override
    public List<Long> getByUserId(Long userId) {
        return sysUserRoleMapper.getByUserId(userId);
    }

    @Override
    public List<String> getRoleMenu(Long roleId) {
        return sysRoleMapper.getRoleMenu(roleId);
    }

    @Override
    public void authMenu(Long roleId, List<Long> menuIds) {
        UserType userType = SecurityHolder.getUserType();
        if (userType != UserType.ADMINISTRATOR) {
            log.warn("为保证系统安全性,非管理员将无法进行菜单授权操作 [{}]", SecurityHolder.getUserId());
            throw new BusinessException(ErrorCode.ADMIN_AUTH);
        }
        sysRoleMapper.deleteRoleMenu(roleId);
        if (CollUtil.isNotEmpty(menuIds)) {
            sysRoleMapper.batchInsert(roleId, menuIds);
        }
    }

    @Override
    public void auth(Long userId, List<Long> roleList) {
        sysUserRoleMapper.deleteByUserId(userId);
        roleList.forEach(roleId -> sysUserRoleMapper.insert(new SysUserRole(userId, roleId)));
    }

    @Override
    public SysRole getById(Long id) {
        return sysRoleMapper.selectById(id);
    }

}
