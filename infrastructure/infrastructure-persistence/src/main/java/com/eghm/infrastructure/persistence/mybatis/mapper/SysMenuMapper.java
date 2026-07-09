package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.sys.menu.MenuQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.po.SysMenuPO;
import com.eghm.vo.sys.menu.MenuFullResponse;
import com.eghm.vo.sys.menu.MenuResponse;
import com.eghm.vo.sys.menu.MenuTreeResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysMenuMapper extends BaseMapper<SysMenuPO> {

    /**
     * 获取所有菜单
     *
     * @return 左侧菜单列表
     */
    List<MenuTreeResponse> getLeftList();

    /**
     * 分页查询菜单
     *
     * @param page 分页参数
     * @param request 查询条件
     * @return 分页列表
     */
    Page<MenuResponse> getByPage(Page<MenuResponse> page, @Param("param") MenuQueryRequest request);

    /**
     * 获取某用户的菜单
     *
     * @param userId 用户id
     * @param grade  空: 全部菜单, 1:导航菜单 2: 按钮菜单
     * @return 用户所有可查看菜单列表
     */
    List<MenuTreeResponse> getMenuList(@Param("userId") Long userId, @Param("grade") Integer grade);

    /**
     * 获取系统级用户的所有菜单
     *
     * @param grade 空: 全部菜单, 1:导航菜单 2: 按钮菜单
     * @return 系统用户的菜单(非商户)
     */
    List<MenuTreeResponse> getSystemMenuList(@Param("grade") Integer grade);

    /**
     * 获取系统所有的菜单
     *
     * @param displayState 符合该状态的额外显示disabled
     * @return 全部菜单
     */
    List<MenuTreeResponse> getAll(@Param("displayState") Integer displayState);

    /**
     * 查询所有菜单
     *
     * @param request 查询条件
     * @return 菜单列表
     */
    List<MenuFullResponse> getList(MenuQueryRequest request);

    /**
     * 查询某个菜单下的最大子菜单id
     *
     * @param pid pid
     * @return 最大子菜单id
     */
    String getMaxId(@Param("pid") String pid);
}
