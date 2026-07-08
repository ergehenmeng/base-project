package com.eghm.sys.repository;

import com.eghm.sys.model.SysMenu;

import java.util.List;

/**
 * 菜单仓储
 *
 * @author 二哥很猛
 */
public interface SysMenuRepository {

    boolean existsTitle(String pid, String title, String excludeId);

    SysMenu findById(String id);

    String findMaxId(String pid);

    void save(SysMenu menu);

    void update(SysMenu menu);

    void deleteById(String id);

    void updateState(String id, Boolean state);

    void updateSort(String id, Integer sortBy);

    List<SysMenu> findEnabledButtons();
}
