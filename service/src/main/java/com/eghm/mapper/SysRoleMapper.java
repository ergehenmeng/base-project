package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询用户所拥有的角色列表
     * @param userId 用户id
     * @return 角色列表
     */
    List<SysRole> getRoleList(@Param("userId") Long userId);

    /**
     * 获取角色拥有的菜单列表
     * @param roleId 角色id
     * @return 菜单menuIds
     */
    List<Long> getRoleMenu(@Param("roleId") Long roleId);

    /**
     * 删除角色菜单关联信息 物理删除
     * @param roleId 角色id
     * @return 删除条件
     */
    int deleteRoleMenu(@Param("roleId") Long roleId);

    /**
     * 批量添加角色菜单关联信息
     * @param roleId 角色id
     * @param menuIdList 菜单列表
     * @return 查询条数
     */
    int batchInsertRoleMenu(@Param("roleId") Long roleId, @Param("menuIdList") List<Long> menuIdList);

    /**
     * 统计用户是否拥有指定角色类型
     * @param userId 用户ID
     * @param roleType 角色类型
     * @return >0 表示有
     */
    int countByRoleType(@Param("userId") Long userId, @Param("roleType") String roleType);
}