package com.eghm.application.system.service;

import com.eghm.application.shared.dto.sys.menu.MenuAddRequest;
import com.eghm.application.shared.dto.sys.menu.MenuEditRequest;
/**
 * @author 二哥很猛
 * @since 2018/1/26 16:14
 */
public interface SysMenuApplicationService {

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

}
