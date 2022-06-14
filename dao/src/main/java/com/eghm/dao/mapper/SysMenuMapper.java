package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 获取某用户的导航菜单
     * @param operatorId 用户id
     * @return 用户所有可查看菜单列表
     */
    List<SysMenu> getMenuList(@Param("operatorId") Long operatorId);

    /**
     * 获取某用户的按钮菜单
     * @param operatorId 用户id
     * @return 用户所有可查看菜单列表
     */
    List<SysMenu> getButtonList(@Param("operatorId")Long operatorId);

    /**
     * 获取某用户的所有菜单
     * @param operatorId 用户id
     * @return 用户所有可查看菜单列表
     */
    List<SysMenu> getList(@Param("operatorId")Long operatorId);

    /**
     * 根据nid与pid查询菜单
     * @param nid nid
     * @param pid pid
     * @return 菜单 默认只查一条
     */
    SysMenu getByNid(@Param("nid") String nid, @Param("pid") String pid);

    /**
     * 查询某个菜单下的最大子菜单id
     * @param pid pid
     * @return 最大子菜单id
     */
    String getMaxId(@Param("pid") String pid);
}