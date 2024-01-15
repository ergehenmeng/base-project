package com.eghm.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.ext.CheckBox;
import com.eghm.dto.role.RoleAddRequest;
import com.eghm.dto.role.RoleEditRequest;
import com.eghm.dto.role.RoleQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.RoleType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.SysRoleMapper;
import com.eghm.mapper.SysUserRoleMapper;
import com.eghm.model.SysRole;
import com.eghm.model.SysUserRole;
import com.eghm.service.business.CommonService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @date 2018/11/26 15:33
 */
@Service("sysRoleService")
@AllArgsConstructor
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    private final CommonService commonService;

    @Override
    public Page<SysRole> getByPage(RoleQueryRequest request) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(SysRole::getRoleType, RoleType.ADMINISTRATOR);
        wrapper.eq(SysRole::getMerchantId, SecurityHolder.getMerchantId());
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), SysRole::getRoleName, request.getQueryName());
        wrapper.orderByDesc(SysRole::getId);
        return sysRoleMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void update(RoleEditRequest request) {
        this.redoRole(request.getRoleName(), request.getId());
        LambdaUpdateWrapper<SysRole> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysRole::getId, request.getId());
        wrapper.eq(SysRole::getMerchantId, request.getMerchantId());
        wrapper.set(SysRole::getRoleName, request.getRoleName());
        wrapper.set(SysRole::getRemark, request.getRemark());
        sysRoleMapper.update(null, wrapper);
    }

    @Override
    public void delete(Long id, Long merchantId) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        if (sysRole == null) {
            return;
        }
        if (sysRole.getRoleType() != RoleType.COMMON && sysRole.getRoleType() != RoleType.MERCHANT) {
            log.info("该角色为系统默认角色,无法删除 [{}] [{}]", id, sysRole.getRoleType());
            throw new BusinessException(ErrorCode.ROLE_FORBID_DELETE);
        }
        LambdaUpdateWrapper<SysRole> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysRole::getId, id);
        wrapper.eq(SysRole::getMerchantId, merchantId);
        wrapper.set(SysRole::getDeleted, true);
        sysRoleMapper.update(null, wrapper);
    }

    @Override
    public void create(RoleAddRequest request) {
        this.redoRole(request.getRoleName(), null);
        SysRole role = DataUtil.copy(request, SysRole.class);
        sysRoleMapper.insert(role);
    }

    @Override
    public List<CheckBox> getList() {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(SysRole::getRoleType, RoleType.ADMINISTRATOR);
        wrapper.eq(SysRole::getMerchantId, SecurityHolder.getMerchantId());
        List<SysRole> roleList = sysRoleMapper.selectList(wrapper);
        return DataUtil.copy(roleList, sysRole -> CheckBox.builder().value(sysRole.getId()).desc(sysRole.getRoleName()).build());
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
    public void authMenu(Long roleId, String menuIds) {
        Long userId = SecurityHolder.getUserId();
        if (!this.isAdminRole(userId)) {
            log.warn("非超级管理员,无法进行菜单授权操作 [{}]", userId);
            throw new BusinessException(ErrorCode.ADMIN_AUTH);
        }
        SysRole sysRole = this.selectByIdRequired(roleId);
        commonService.checkIllegal(sysRole.getMerchantId());

        sysRoleMapper.deleteRoleMenu(roleId);
        if (StrUtil.isNotBlank(menuIds)) {
            List<Long> menuIdList = StrUtil.split(menuIds, ",").stream().mapToLong(Long::parseLong)
                    .boxed().collect(Collectors.toList());
            sysRoleMapper.batchInsertRoleMenu(roleId, menuIdList);
        }
    }

    @Override
    public void authRole(Long userId, String roleIds) {
        sysUserRoleMapper.deleteByUserId(userId);
        if (StrUtil.isNotBlank(roleIds)) {
            List<String> roleStringList = StrUtil.split(roleIds, ',');
            //循环插入角色关联信息
            roleStringList.forEach(s -> sysUserRoleMapper.insert(new SysUserRole(userId, Long.parseLong(s))));
        }
    }

    @Override
    public void authRole(Long userId, List<RoleType> roleList) {
        sysUserRoleMapper.deleteByUserId(userId);
        if (CollUtil.isEmpty(roleList)) {
            return;
        }
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.select(SysRole::getId);
        wrapper.in(SysRole::getRoleType, roleList);
        List<SysRole> selectList = sysRoleMapper.selectList(wrapper);
        for (SysRole sysRole : selectList) {
            sysUserRoleMapper.insert(new SysUserRole(userId, sysRole.getId()));
        }
    }

    @Override
    public void auth(Long userId, List<Long> roleList) {
        sysUserRoleMapper.deleteByUserId(userId);
        roleList.forEach(roleId -> sysUserRoleMapper.insert(new SysUserRole(userId, roleId)));
    }

    @Override
    public boolean isAdminRole(Long userId) {
        return sysRoleMapper.countByRoleType(userId, RoleType.ADMINISTRATOR.getValue()) > 0;
    }

    @Override
    public SysRole selectByIdRequired(Long roleId) {
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        if (sysRole == null) {
            log.warn("角色不存在 [{}]", roleId);
            throw new BusinessException(ErrorCode.ROLE_NULL);
        }
        return sysRole;
    }

    /**
     * 校验角色是否重复
     * @param name 角色名称
     * @param id id 编辑时不能为空
     */
    public void redoRole(String name, Long id) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysRole::getRoleName, name);
        wrapper.ne(id != null, SysRole::getId, id);
        Long count = sysRoleMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("角色名称重复[{}] [{}]", name, id);
            throw new BusinessException(ErrorCode.ROLE_NAME_REDO);
        }
    }
}
