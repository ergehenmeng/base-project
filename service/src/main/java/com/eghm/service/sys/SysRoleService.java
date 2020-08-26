package com.eghm.service.sys;

import com.eghm.model.dto.sys.role.RoleAddRequest;
import com.eghm.model.dto.sys.role.RoleEditRequest;
import com.eghm.model.dto.sys.role.RoleQueryRequest;
import com.eghm.dao.model.sys.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/26 15:33
 */
public interface SysRoleService {

    /**
     * 分页查询角色信息
     * @param request 前台查询条件
     * @return 列表
     */
    PageInfo<SysRole> getByPage(RoleQueryRequest request);

    /**
     * 根据主键查询角色信息
     * @param id 主键
     * @return 角色信息
     */
    SysRole getById(int id);

    /**
     * 更新角色信息
     * @param request 前台参数
     */
    void updateRole(RoleEditRequest request);

    /**
     * 删除角色信息
     * @param id 主键
     */
    void deleteRole(int id);

    /**
     * 添加角色信息
     * @param request 前台参数
     */
    void addRole(RoleAddRequest request);

    /**
     * 获取所有可用的用户角色
     * @return 角色列表
     */
    List<SysRole> getList();

    /**
     * 获取管理人员所拥有的角色id
     * @param operatorId 管理人员id
     * @return 角色id列表
     */
    List<Integer> getByOperatorId(Integer operatorId);

    /**
     * 获取角色的菜单列表
     * @param roleId 角色
     * @return 菜单列表
     */
    List<Integer> getRoleMenu(Integer roleId);

    /**
     * 角色菜单权限保存
     * @param roleId 角色id
     * @param menuIds 菜单ids
     */
    void authMenu(Integer roleId, String menuIds);

    /**
     * 获取用户角色列表
     * @param operatorId 用户id
     * @return 角色列表
     */
    List<SysRole> getRoleList(Integer operatorId);
}

