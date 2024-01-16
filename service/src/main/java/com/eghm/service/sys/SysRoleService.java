package com.eghm.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.CheckBox;
import com.eghm.dto.role.RoleAddRequest;
import com.eghm.dto.role.RoleEditRequest;
import com.eghm.dto.role.RoleQueryRequest;
import com.eghm.enums.ref.RoleType;
import com.eghm.model.SysRole;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/26 15:33
 */
public interface SysRoleService {

    /**
     * 分页查询角色信息
     *
     * @param request 前台查询条件
     * @return 列表
     */
    Page<SysRole> getByPage(RoleQueryRequest request);

    /**
     * 更新角色信息
     *
     * @param request 前台参数
     */
    void update(RoleEditRequest request);

    /**
     * 删除角色信息
     *
     * @param id 主键
     */
    void delete(Long id, Long merchantId);

    /**
     * 添加角色信息
     *
     * @param request 前台参数
     */
    void create(RoleAddRequest request);

    /**
     * 获取所有可用的用户角色
     *
     * @return 角色列表
     */
    List<CheckBox> getList();

    /**
     * 获取管理人员所拥有的角色id
     *
     * @param userId 管理人员id
     * @return 角色id列表
     */
    List<Long> getByUserId(Long userId);

    /**
     * 获取角色的菜单列表
     *
     * @param roleId 角色
     * @return 菜单列表
     */
    List<String> getRoleMenu(Long roleId);

    /**
     * 角色菜单关联关系保存
     *
     * @param roleId  角色id
     * @param menuIds 菜单ids
     */
    void authMenu(Long roleId, String menuIds);

    /**
     * 商户角色授权 (角色类型)
     *
     * @param userId   商户对应于用户id
     * @param roleList 商户角色code
     */
    void authRole(Long userId, List<RoleType> roleList);

    /**
     * 商户角色授权 (角色id)
     *
     * @param userId   商户对应于用户id
     * @param roleList 角色id
     */
    void auth(Long userId, List<Long> roleList);

    /**
     * 判断用户是否有超管角色
     *
     * @param userId 用户ID
     * @return true:有管理员角色 false:没有
     */
    boolean isAdminRole(Long userId);

    /**
     * 根据角色id查询角色信息
     *
     * @param roleId 角色id
     * @return 角色信息
     */
    SysRole selectByIdRequired(Long roleId);
}

