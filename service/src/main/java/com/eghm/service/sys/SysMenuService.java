package com.eghm.service.sys;

import com.eghm.dao.model.SysMenu;
import com.eghm.model.dto.menu.MenuAddRequest;
import com.eghm.model.dto.menu.MenuEditRequest;
import com.eghm.model.vo.menu.MenuResponse;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/1/26 16:14
 */
public interface SysMenuService {

    /**
     * 获取用户导航菜单列表,不包含按钮菜单
     * @param operatorId 用户id
     * @return 菜单列表(一级菜单 内部包含二级菜单)
     */
    List<MenuResponse> getLeftMenuList(Long operatorId);

    /**
     * 获取所有导航菜单列表,不包含按钮菜单
     * @return 菜单列表(一级菜单 内部包含二级菜单)
     */
    List<MenuResponse> getLeftMenuList();

    /**
     * 获取用户按钮,导航菜单列表
     * @param operatorId 用户id
     * @return 菜单列表
     */
    List<SysMenu> getList(Long operatorId);

    /**
     * 获取所有可用的菜单
     * @return 菜单列表
     */
    List<SysMenu> getList();

    /**
     * 添加菜单
     * @param request 要添加的菜单信息
     */
    void create(MenuAddRequest request);

    /**
     * 更新菜单信息
     * @param request 要更新的菜单信息
     */
    void update(MenuEditRequest request);

    /**
     * 根据主键删除菜单
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 查询用户的菜单权限标识符
     * @param operator 用户id
     * @return 菜单标示符
     */
    List<String> getAuth(Long operator);

    /**
     * 查询所有菜单权限
     * @return 菜单标识符
     */
    List<String> getAuth();
}
