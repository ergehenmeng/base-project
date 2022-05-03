package com.eghm.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.mapper.SysOperatorRoleMapper;
import com.eghm.dao.mapper.SysRoleMapper;
import com.eghm.dao.model.SysOperatorRole;
import com.eghm.dao.model.SysRole;
import com.eghm.model.dto.role.RoleAddRequest;
import com.eghm.model.dto.role.RoleEditRequest;
import com.eghm.model.dto.role.RoleQueryRequest;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 二哥很猛
 * @date 2018/11/26 15:33
 */
@Service("sysRoleService")
@AllArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;

    private final SysOperatorRoleMapper sysOperatorRoleMapper;

    @Override
    public Page<SysRole> getByPage(RoleQueryRequest request) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysRole::getDeleted, false);
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), SysRole::getRoleName, request.getQueryName());
        return sysRoleMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public SysRole getById(Long id) {
        return sysRoleMapper.selectById(id);
    }

    @Override
    public void updateRole(RoleEditRequest request) {
        SysRole role = DataUtil.copy(request, SysRole.class);
        sysRoleMapper.updateById(role);
    }

    @Override
    public void deleteRole(Long id) {
        SysRole role = new SysRole();
        role.setDeleted(true);
        sysRoleMapper.updateById(role);
    }

    @Override
    public void addRole(RoleAddRequest request) {
        SysRole role = DataUtil.copy(request, SysRole.class);
        sysRoleMapper.insert(role);
    }

    @Override
    public List<SysRole> getList() {
        return sysRoleMapper.selectList(null);
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
            List<Long> menuIdList = Stream.of(StrUtil.split(menuIds, ",")).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
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
}
