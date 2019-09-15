package com.fanyin.dao.mapper.system;

import com.fanyin.dao.model.system.SystemMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SystemMenuMapper {


    /**
     * 插入不为空的记录
     *
     * @param record
     * @return
     */
    int insertSelective(SystemMenu record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     * @return
     */
    SystemMenu selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录<br>
     * 已删除的不在更新范围内<br/>
     * 新增时确认的字段不在更新范围内
     * @param record 待更新的字段
     * @return 成功更新的条数
     */
    int updateByPrimaryKeySelective(SystemMenu record);

    /**
     * 查询所有可用的菜单
     * @return 所有菜单
     */
    List<SystemMenu> getAllList();

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
    List<SystemMenu> getMenuList(@Param("operatorId") Integer operatorId);

    /**
     * 获取某用户的按钮菜单
     * @param operatorId 用户id
     * @return 用户所有可查看菜单列表
     */
    List<SystemMenu> getButtonList(@Param("operatorId")Integer operatorId);

    /**
     * 获取某用户的所有菜单
     * @param operatorId 用户id
     * @return 用户所有可查看菜单列表
     */
    List<SystemMenu> getList(@Param("operatorId")Integer operatorId);

    /**
     * 根据nid与pid查询菜单
     * @param nid nid
     * @param pid pid
     * @return 菜单 默认只查一条
     */
    SystemMenu getByNid(@Param("nid") String nid, @Param("pid") Integer pid);
}