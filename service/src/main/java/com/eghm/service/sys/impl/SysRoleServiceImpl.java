package com.eghm.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import com.eghm.dao.mapper.system.SysOperatorRoleMapper;
import com.eghm.dao.mapper.system.SysRoleMapper;
import com.eghm.dao.model.system.SysRole;
import com.eghm.model.dto.sys.role.RoleAddRequest;
import com.eghm.model.dto.sys.role.RoleEditRequest;
import com.eghm.model.dto.sys.role.RoleQueryRequest;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 二哥很猛
 * @date 2018/11/26 15:33
 */
@Service("sysRoleService")
@Transactional(rollbackFor = RuntimeException.class)
public class SysRoleServiceImpl implements SysRoleService {

    private SysRoleMapper sysRoleMapper;

    private SysOperatorRoleMapper sysOperatorRoleMapper;

    @Autowired
    public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    @Autowired
    public void setSysOperatorRoleMapper(SysOperatorRoleMapper sysOperatorRoleMapper) {
        this.sysOperatorRoleMapper = sysOperatorRoleMapper;
    }

    @Override
    public PageInfo<SysRole> getByPage(RoleQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<SysRole> list = sysRoleMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public SysRole getById(int id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateRole(RoleEditRequest request) {
        SysRole role = DataUtil.copy(request, SysRole.class);
        sysRoleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteRole(int id) {
        SysRole role = new SysRole();
        role.setDeleted(true);
        sysRoleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void addRole(RoleAddRequest request) {
        SysRole role = DataUtil.copy(request, SysRole.class);
        role.setDeleted(false);
        sysRoleMapper.insertSelective(role);
    }

    @Override
    public List<SysRole> getList() {
        RoleQueryRequest request = new RoleQueryRequest();
        return sysRoleMapper.getList(request);
    }

    @Override
    public List<Integer> getByOperatorId(Integer operatorId) {
        return sysOperatorRoleMapper.getByOperatorId(operatorId);
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return sysRoleMapper.getRoleMenu(roleId);
    }

    @Override
    public void authMenu(Integer roleId, String menuIds) {
        sysRoleMapper.deleteRoleMenu(roleId);
        if (StringUtils.isNotEmpty(menuIds)) {
            List<Integer> menuIdList = Stream.of(StrUtil.split(menuIds, ",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            sysRoleMapper.batchInsertRoleMenu(roleId, menuIdList);
        }
    }

    @Override
    public List<SysRole> getRoleList(Integer operatorId) {
        return sysRoleMapper.getRoleList(operatorId);
    }
}
