package com.eghm.application.system.service;

import com.eghm.application.shared.dto.sys.role.RoleAddRequest;
import com.eghm.application.shared.dto.sys.role.RoleEditRequest;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/11/26 15:33
 */
public interface SysRoleApplicationService {

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
    void delete(Long id);

    /**
     * 添加角色信息
     *
     * @param request 前台参数
     */
    void create(RoleAddRequest request);

    /**
     * 角色菜单关联关系保存
     *
     * @param roleId  角色id
     * @param menuIds 菜单ids
     */
    void authMenu(Long roleId, List<Long> menuIds);

    /**
     * 商户角色授权 (角色id)
     *
     * @param userId   商户对应于用户id
     * @param roleList 角色id
     */
    void auth(Long userId, List<Long> roleList);

}

