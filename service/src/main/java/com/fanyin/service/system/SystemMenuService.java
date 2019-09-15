package com.fanyin.service.system;

import com.fanyin.model.dto.system.menu.MenuAddRequest;
import com.fanyin.model.dto.system.menu.MenuEditRequest;
import com.fanyin.dao.model.system.SystemMenu;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/1/26 16:14
 */
public interface SystemMenuService {

    /**
     * 获取用户导航菜单列表,不包含按钮菜单
     * @param operatorId 用户id
     * @return 菜单列表(一级菜单 内部包含二级菜单)
     */
    List<SystemMenu> getMenuList(Integer operatorId);

    /**
     * 获取用户按钮菜单列表
     * @param operatorId 用户id
     * @return 菜单列表
     */
    List<SystemMenu> getButtonList(Integer operatorId);

    /**
     * 获取用户按钮,导航菜单列表
     * @param operatorId 用户id
     * @return 菜单列表
     */
    List<SystemMenu> getList(Integer operatorId);

    /**
     * 根据主键查询菜单
     * @param id 主键
     * @return 单个菜单
     */
    SystemMenu getMenuById(Integer id);

    /**
     * 获取所有可用的菜单
     * @return 菜单列表
     */
    List<SystemMenu> getAllList();

    /**
     * 添加菜单
     * @param request 要添加的菜单信息
     */
    void addMenu(MenuAddRequest request);

    /**
     * 更新菜单信息
     * @param request 要更新的菜单信息
     */
    void updateMenu(MenuEditRequest request);

    /**
     * 根据主键删除菜单
     * @param id 主键
     */
    void deleteMenu(Integer id);

    /**
     * 获取用户菜单权限 spring security
     * @param operator 用户id
     * @return 列表
     */
    List<GrantedAuthority> getAuthorityByOperatorId(Integer operator);
}
