package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.menu.MenuQueryRequest;
import com.eghm.model.SysMenu;
import com.eghm.vo.menu.MenuBaseResponse;
import com.eghm.vo.menu.MenuResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 分页查询菜单
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 菜单列表
     */
    Page<MenuBaseResponse> getByPage(Page<MenuBaseResponse> page, @Param("param") MenuQueryRequest request);

    /**
     * 获取某用户的菜单
     *
     * @param userId 用户id
     * @param grade  空: 全部菜单, 1:导航菜单 2: 按钮菜单
     * @return 用户所有可查看菜单列表
     */
    List<MenuResponse> getMenuList(@Param("userId") Long userId, @Param("grade") Integer grade);

    /**
     * 获取系统所有的菜单
     *
     * @param grade 空: 全部菜单, 1:导航菜单 2: 按钮菜单
     * @return 用户所有可查看菜单列表
     */
    List<MenuResponse> getAdminMenuList(@Param("grade") Integer grade);

    /**
     * 获取商户所有的菜单
     *
     * @param grade 空: 全部菜单, 1:导航菜单 2: 按钮菜单
     * @return 用户所有可查看菜单列表
     */
    List<MenuResponse> getMerchantMenuList(@Param("grade") Integer grade);

    /**
     * 查询所有菜单
     *
     * @return 菜单列表
     */
    List<MenuResponse> getList();

    /**
     * 查询某个菜单下的最大子菜单id
     *
     * @param pid pid
     * @return 最大子菜单id
     */
    String getMaxId(@Param("pid") String pid);
}