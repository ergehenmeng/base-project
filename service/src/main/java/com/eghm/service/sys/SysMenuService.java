package com.eghm.service.sys;

import com.eghm.dto.menu.MenuAddRequest;
import com.eghm.dto.menu.MenuEditRequest;
import com.eghm.model.SysMenu;
import com.eghm.vo.menu.MenuResponse;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/1/26 16:14
 */
public interface SysMenuService {

    /**
     * 获取用户导航菜单列表,不包含按钮菜单
     * @param userId 用户id
     * @return 菜单列表(一级菜单 内部包含二级菜单)
     */
    List<MenuResponse> getLeftMenuList(Long userId);

    /**
     * 获取超管所有导航菜单列表,不包含按钮菜单
     * @return 菜单列表(一级菜单 内部包含二级菜单)
     */
    List<MenuResponse> getAdminLeftMenuList();

    /**
     * 获取系统所有导航菜单列表,按钮
     * @return 菜单列表
     */
    List<MenuResponse> getSystemList();

    /**
     * 获取商户所有导航菜单列表,按钮
     * @return 菜单列表
     */
    List<MenuResponse> getMerchantList();

    /**
     * 获取所有可用的菜单+按钮菜单
     * @return 菜单列表
     */
    List<MenuResponse> getList();

    /**
     * 获取所有可用的按钮菜单
     * @return 按钮列表
     */
    List<SysMenu> getButtonList();

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
    void delete(String id);

    /**
     * 更新菜单状态
     * @param id id
     * @param state 0:禁用 1:启用
     */
    void updateState(String id, Integer state);

    /**
     * 查询用户的菜单权限标识符
     * @param userId 用户id
     * @return 菜单标示符
     */
    List<String> getPermCode(Long userId);

    /**
     * 查询所有菜单权限
     * @return 菜单标识符
     */
    List<String> getAdminPermCode();
}
