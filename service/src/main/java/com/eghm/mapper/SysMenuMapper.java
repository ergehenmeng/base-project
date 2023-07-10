package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.SysMenu;
import com.eghm.vo.menu.MenuResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 获取某用户的菜单
     * @param userId 用户id
     * @param grade 空: 全部菜单, 1:导航菜单 2: 按钮菜单
     * @return 用户所有可查看菜单列表
     */
    List<MenuResponse> getMenuList(@Param("userId") Long userId, @Param("state") Integer grade);

    /**
     * 获取超管的菜单
     * @param grade 空: 全部菜单, 1:导航菜单 2: 按钮菜单
     * @return 用户所有可查看菜单列表
     */
    List<MenuResponse> getAdminMenuList(@Param("state") Integer grade);

    /**
     * 查询某个菜单下的最大子菜单id
     * @param pid pid
     * @return 最大子菜单id
     */
    String getMaxId(@Param("pid") String pid);
}