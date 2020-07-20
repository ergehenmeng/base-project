package com.eghm.service.system.impl;

import com.eghm.dao.mapper.system.SystemOperatorRoleMapper;
import com.eghm.dao.mapper.system.SystemRoleMapper;
import com.eghm.dao.model.system.SystemRole;
import com.eghm.model.dto.system.role.RoleAddRequest;
import com.eghm.model.dto.system.role.RoleEditRequest;
import com.eghm.model.dto.system.role.RoleQueryRequest;
import com.eghm.service.system.SystemRoleService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @date 2018/11/26 15:33
 */
@Service("systemRoleService")
@Transactional(rollbackFor = RuntimeException.class)
public class SystemRoleServiceImpl implements SystemRoleService {

    private SystemRoleMapper systemRoleMapper;

    private SystemOperatorRoleMapper systemOperatorRoleMapper;

    @Autowired
    public void setSystemRoleMapper(SystemRoleMapper systemRoleMapper) {
        this.systemRoleMapper = systemRoleMapper;
    }

    @Autowired
    public void setSystemOperatorRoleMapper(SystemOperatorRoleMapper systemOperatorRoleMapper) {
        this.systemOperatorRoleMapper = systemOperatorRoleMapper;
    }

    @Override
    public PageInfo<SystemRole> getByPage(RoleQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<SystemRole> list = systemRoleMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public SystemRole getById(int id) {
        return systemRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateRole(RoleEditRequest request) {
        SystemRole role = DataUtil.copy(request, SystemRole.class);
        systemRoleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteRole(int id) {
        SystemRole role = new SystemRole();
        role.setDeleted(true);
        systemRoleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void addRole(RoleAddRequest request) {
        SystemRole role = DataUtil.copy(request, SystemRole.class);
        role.setDeleted(false);
        systemRoleMapper.insertSelective(role);
    }

    @Override
    public List<SystemRole> getList() {
        RoleQueryRequest request = new RoleQueryRequest();
        return systemRoleMapper.getList(request);
    }

    @Override
    public List<Integer> getByOperatorId(Integer operatorId) {
        return systemOperatorRoleMapper.getByOperatorId(operatorId);
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return systemRoleMapper.getRoleMenu(roleId);
    }

    @Override
    public void authMenu(Integer roleId, String menuIds) {
        systemRoleMapper.deleteRoleMenu(roleId);
        if (StringUtils.isNotEmpty(menuIds)) {
            List<Integer> menuIdList = Splitter.on(",").splitToList(menuIds).stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            systemRoleMapper.batchInsertRoleMenu(roleId, menuIdList);
        }
    }
}
