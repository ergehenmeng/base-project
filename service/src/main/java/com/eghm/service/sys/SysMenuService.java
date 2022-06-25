package com.eghm.service.sys;

import com.eghm.model.dto.menu.MenuAddRequest;
import com.eghm.model.dto.menu.MenuEditRequest;
import com.eghm.dao.model.SysMenu;
import org.springframework.security.core.GrantedAuthority;

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
    List<SysMenu> getMenuList(Long operatorId);

    /**
     * 获取用户按钮菜单列表
     * @param operatorId 用户id
     * @return 菜单列表
     */
    List<SysMenu> getButtonList(Long operatorId);

    /**
     * 获取用户按钮,导航菜单列表
     * @param operatorId 用户id
     * @return 菜单列表
     */
    List<SysMenu> getList(Long operatorId);

    /**
     * 根据主键查询菜单
     * @param id 主键
     * @return 单个菜单
     */
    SysMenu getMenuById(Long id);

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
     * 查询用户的菜单权限
     * @param operator 用户id
     * @return 菜单标示符
     */
    List<String> getAuthByOperatorId(Long operator);
}
