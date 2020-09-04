package com.eghm.dao.mapper;

import com.eghm.model.dto.role.RoleQueryRequest;
import com.eghm.dao.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysRoleMapper {

    /**
     * 插入不为空的记录
     *
     * @param record
     */
    int insertSelective(SysRole record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SysRole selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SysRole record);

    /**
     * 根据条件查询角色信息
     * @param request 请求参数
     * @return 角色信息列表
     */
    List<SysRole> getList(RoleQueryRequest request);

    /**
     * 查询用户所拥有的角色列表
     * @param operatorId 用户id
     * @return 角色列表
     */
    List<SysRole> getRoleList(@Param("operatorId") Integer operatorId);

    /**
     * 获取角色拥有的菜单列表
     * @param roleId 角色id
     * @return 菜单menuIds
     */
    List<Integer> getRoleMenu(@Param("roleId") Integer roleId);

    /**
     * 删除角色菜单关联信息 物理删除
     * @param roleId 角色id
     * @return 删除条件
     */
    int deleteRoleMenu(@Param("roleId") Integer roleId);

    /**
     * 批量添加角色菜单关联信息
     * @param roleId 角色id
     * @param menuIdList 菜单列表
     * @return 查询条数
     */
    int batchInsertRoleMenu(@Param("roleId") Integer roleId, @Param("menuIdList") List<Integer> menuIdList);
}