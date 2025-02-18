package com.eghm.service.sys.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.ext.CheckBox;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.sys.role.RoleAddRequest;
import com.eghm.dto.sys.role.RoleEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.RoleType;
import com.eghm.enums.UserType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.SysRoleMapper;
import com.eghm.mapper.SysUserRoleMapper;
import com.eghm.model.SysRole;
import com.eghm.model.SysUserRole;
import com.eghm.service.business.CommonService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.sys.SysRoleResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/11/26 15:33
 */
@Service("sysRoleService")
@AllArgsConstructor
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;

    private final CommonService commonService;

    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    public Page<SysRoleResponse> getByPage(PagingQuery request) {
        Long merchantId = SecurityHolder.getMerchantId();
        return sysRoleMapper.getByPage(request.createPage(), request.getQueryName(), merchantId);
    }

    @Override
    public void update(RoleEditRequest request) {
        this.redoRole(request.getRoleName(), request.getId(), request.getMerchantId());
        LambdaUpdateWrapper<SysRole> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysRole::getId, request.getId());
        if (request.getMerchantId() == null) {
            wrapper.isNull(SysRole::getMerchantId);
        } else {
            wrapper.eq(SysRole::getMerchantId, request.getMerchantId());
        }
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
        if (merchantId != null) {
            wrapper.eq(SysRole::getMerchantId, merchantId);
        } else {
            wrapper.isNull(SysRole::getMerchantId);
        }
        wrapper.set(SysRole::getDeleted, true);
        sysRoleMapper.update(null, wrapper);
    }

    @Override
    public void create(RoleAddRequest request) {
        this.redoRole(request.getRoleName(), null, request.getMerchantId());
        SysRole role = DataUtil.copy(request, SysRole.class);
        sysRoleMapper.insert(role);
    }

    @Override
    public List<CheckBox> getList() {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        Long merchantId = SecurityHolder.getMerchantId();
        if (merchantId != null) {
            wrapper.eq(SysRole::getMerchantId, merchantId);
        } else {
            wrapper.eq(SysRole::getRoleType, RoleType.COMMON);
        }
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
    public void authMenu(Long roleId, List<Long> menuIds) {
        UserType userType = SecurityHolder.getUserType();
        if (userType != UserType.ADMINISTRATOR && userType != UserType.MERCHANT_ADMIN) {
            log.warn("为保证系统安全性,非管理员将无法进行菜单授权操作 [{}]", SecurityHolder.getUserId());
            throw new BusinessException(ErrorCode.ADMIN_AUTH);
        }
        SysRole sysRole = this.selectByIdRequired(roleId);
        commonService.checkIllegal(sysRole.getMerchantId());
        sysRoleMapper.deleteRoleMenu(roleId);
        if (CollUtil.isNotEmpty(menuIds)) {
            sysRoleMapper.batchInsertRoleMenu(roleId, menuIds);
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

    /**
     * 根据角色id查询角色信息
     *
     * @param roleId 角色id
     * @return 角色信息
     */
    private SysRole selectByIdRequired(Long roleId) {
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        if (sysRole == null) {
            log.warn("角色不存在 [{}]", roleId);
            throw new BusinessException(ErrorCode.ROLE_NULL);
        }
        return sysRole;
    }

    /**
     * 校验角色是否重复
     *
     * @param name 角色名称
     * @param id   id 编辑时不能为空
     */
    public void redoRole(String name, Long id, Long merchantId) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysRole::getRoleName, name);
        wrapper.eq(merchantId != null, SysRole::getMerchantId, merchantId);
        wrapper.ne(id != null, SysRole::getId, id);
        Long count = sysRoleMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("角色名称重复[{}] [{}]", name, id);
            throw new BusinessException(ErrorCode.ROLE_NAME_REDO);
        }
    }
}
