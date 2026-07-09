package com.eghm.application.system.service;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.menu.MenuAddRequest;
import com.eghm.application.shared.dto.sys.menu.MenuEditRequest;
import com.eghm.application.shared.dto.sys.menu.MenuQueryRequest;
import com.eghm.domain.system.model.SysMenu;
import com.eghm.application.shared.vo.sys.menu.MenuFullResponse;
import com.eghm.application.shared.vo.sys.menu.MenuResponse;
import com.eghm.application.shared.vo.sys.menu.MenuTreeResponse;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/1/26 16:14
 */
public interface SysMenuApplicationService {

    /**
     * 左侧菜单
     *
     * @return list
     */
    MenuTreeResponse tree();

    /**
     * 获取所有可用的菜单+按钮菜单
     *
     * @param request 查询条件
     * @return 菜单列表
     */
    Page<MenuResponse> getByPage(MenuQueryRequest request);

    /**
     * 获取用户导航菜单列表,不包含按钮菜单
     *
     * @param userId 用户id
     * @return 菜单列表(一级菜单 内部包含二级菜单)
     */
    List<MenuTreeResponse> getLeftMenuList(Long userId);

    /**
     * 获取超管所有导航菜单列表,不包含按钮菜单
     *
     * @return 菜单列表(一级菜单 内部包含二级菜单)
     */
    List<MenuTreeResponse> getAdminLeftMenuList();

    /**
     * 获取系统所有导航菜单列表,按钮
     *
     * @param displayState 根据该状态决定是否回写disabled字段, 为空则不显示
     * @return 菜单列表
     */
    List<MenuTreeResponse> getAll(Integer displayState);

    /**
     * 获取所有可用的菜单+按钮菜单
     *
     * @param request 查询条件
     * @return 菜单列表
     */
    List<MenuFullResponse> getList(MenuQueryRequest request);

    /**
     * 获取所有可用的按钮菜单
     *
     * @return 按钮列表
     */
    List<SysMenu> getButtonList();

    /**
     * 添加菜单
     *
     * @param request 要添加的菜单信息
     */
    void create(MenuAddRequest request);

    /**
     * 更新菜单信息
     *
     * @param request 要更新的菜单信息
     */
    void update(MenuEditRequest request);

    /**
     * 根据主键删除菜单
     *
     * @param id 主键
     */
    void delete(String id);

    /**
     * 更新菜单状态
     *
     * @param id    id
     * @param state false:禁用 true:启用
     */
    void updateState(String id, Boolean state);

    /**
     * 更新菜单排序
     *
     * @param id     id
     * @param sortBy 排序
     */
    void sortBy(String id, Integer sortBy);

    /**
     * 查询用户的菜单权限标识符
     *
     * @param userId 用户id
     * @return 菜单标示符
     */
    List<String> getPermCode(Long userId);

    /**
     * 查询所有菜单权限
     *
     * @return 菜单标识符
     */
    List<String> getAdminPermCode();
}
