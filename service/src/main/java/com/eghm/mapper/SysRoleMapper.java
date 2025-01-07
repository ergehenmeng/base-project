package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.SysRole;
import com.eghm.vo.sys.SysRoleResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 分页查询
     *
     * @param page 分页
     * @param queryName 查询条件
     * @param merchantId 商户id
     * @return 列表
     */
    Page<SysRoleResponse> getByPage(Page<SysRoleResponse> page, @Param("queryName") String queryName, @Param("merchantId") Long merchantId);

    /**
     * 获取角色拥有的菜单列表
     *
     * @param roleId 角色id
     * @return 菜单menuIds
     */
    List<String> getRoleMenu(@Param("roleId") Long roleId);

    /**
     * 删除角色菜单关联信息 物理删除
     *
     * @param roleId 角色id
     */
    void deleteRoleMenu(@Param("roleId") Long roleId);

    /**
     * 批量添加角色菜单关联信息
     *
     * @param roleId     角色id
     * @param menuIdList 菜单列表
     */
    void batchInsertRoleMenu(@Param("roleId") Long roleId, @Param("menuIdList") List<Long> menuIdList);

}