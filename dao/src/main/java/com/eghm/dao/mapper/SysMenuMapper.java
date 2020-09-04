package com.eghm.dao.mapper;

import com.eghm.dao.model.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysMenuMapper {


    /**
     * 插入不为空的记录
     *
     * @param record
     * @return
     */
    int insertSelective(SysMenu record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     * @return
     */
    SysMenu selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录<br>
     * 已删除的不在更新范围内<br/>
     * 新增时确认的字段不在更新范围内
     * @param record 待更新的字段
     * @return 成功更新的条数
     */
    int updateByPrimaryKeySelective(SysMenu record);

    /**
     * 查询所有可用的菜单
     * @return 所有菜单
     */
    List<SysMenu> getAllList();

    /**
     * 删除菜单
     * @param id 主键
     * @return 影响条数
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 获取某用户的导航菜单
     * @param operatorId 用户id
     * @return 用户所有可查看菜单列表
     */
    List<SysMenu> getMenuList(@Param("operatorId") Integer operatorId);

    /**
     * 获取某用户的按钮菜单
     * @param operatorId 用户id
     * @return 用户所有可查看菜单列表
     */
    List<SysMenu> getButtonList(@Param("operatorId")Integer operatorId);

    /**
     * 获取某用户的所有菜单
     * @param operatorId 用户id
     * @return 用户所有可查看菜单列表
     */
    List<SysMenu> getList(@Param("operatorId")Integer operatorId);

    /**
     * 根据nid与pid查询菜单
     * @param nid nid
     * @param pid pid
     * @return 菜单 默认只查一条
     */
    SysMenu getByNid(@Param("nid") String nid, @Param("pid") Integer pid);
}