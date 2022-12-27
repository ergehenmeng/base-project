package com.eghm.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.RoleType;
import com.eghm.common.exception.BusinessException;
import com.eghm.mapper.SysOperatorRoleMapper;
import com.eghm.mapper.SysRoleMapper;
import com.eghm.model.SysOperatorRole;
import com.eghm.model.SysRole;
import com.eghm.model.dto.role.RoleAddRequest;
import com.eghm.model.dto.role.RoleEditRequest;
import com.eghm.model.dto.role.RoleQueryRequest;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    private final SysOperatorRoleMapper sysOperatorRoleMapper;

    @Override
    public Page<SysRole> getByPage(RoleQueryRequest request) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(SysRole::getRoleType, RoleType.ADMINISTRATOR);
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), SysRole::getRoleName, request.getQueryName());
        return sysRoleMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public SysRole getById(Long id) {
        return sysRoleMapper.selectById(id);
    }

    @Override
    public void update(RoleEditRequest request) {
        this.redoRole(request.getRoleName(), request.getId());
        SysRole role = DataUtil.copy(request, SysRole.class);
        sysRoleMapper.updateById(role);
    }

    @Override
    public void delete(Long id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        if (sysRole.getRoleType() != RoleType.COMMON) {
            log.info("该角色为系统默认角色,无法删除 [{}] [{}]", id, sysRole.getRoleType());
            throw new BusinessException(ErrorCode.ROLE_FORBID_DELETE);
        }
        sysRoleMapper.deleteById(id);
    }

    @Override
    public void create(RoleAddRequest request) {
        this.redoRole(request.getRoleName(), null);
        SysRole role = DataUtil.copy(request, SysRole.class);
        role.setRoleType(RoleType.COMMON);
        sysRoleMapper.insert(role);
    }

    @Override
    public List<SysRole> getList() {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(SysRole::getRoleType, RoleType.ADMINISTRATOR);
        return sysRoleMapper.selectList(wrapper);
    }

    @Override
    public List<Long> getByOperatorId(Long operatorId) {
        return sysOperatorRoleMapper.getByOperatorId(operatorId);
    }

    @Override
    public List<Long> getRoleMenu(Long roleId) {
        return sysRoleMapper.getRoleMenu(roleId);
    }

    @Override
    public void authMenu(Long roleId, String menuIds) {
        sysRoleMapper.deleteRoleMenu(roleId);
        if (StringUtils.isNotEmpty(menuIds)) {
            List<Long> menuIdList = StrUtil.split(menuIds, ",").stream().mapToLong(Long::parseLong)
                    .boxed().collect(Collectors.toList());
            sysRoleMapper.batchInsertRoleMenu(roleId, menuIdList);
        }
    }

    @Override
    public void authRole(Long operatorId, String roleIds) {
        sysOperatorRoleMapper.deleteByOperatorId(operatorId);
        if (StrUtil.isNotBlank(roleIds)) {
            List<String> roleStringList = StrUtil.split(roleIds, ',');
            //循环插入角色关联信息
            roleStringList.forEach(s -> sysOperatorRoleMapper.insert(new SysOperatorRole(operatorId, Long.parseLong(s))));
        }
    }

    @Override
    public List<SysRole> getRoleList(Long operatorId) {
        return sysRoleMapper.getRoleList(operatorId);
    }

    @Override
    public boolean isAdminRole(Long operatorId) {
        return sysRoleMapper.countByRoleType(operatorId, RoleType.ADMINISTRATOR.getValue()) > 0;
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
        Integer count = sysRoleMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("角色名称重复[{}] [{}]", name, id);
            throw new BusinessException(ErrorCode.ROLE_NAME_REDO);
        }
    }
}
