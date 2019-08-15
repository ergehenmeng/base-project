package com.fanyin.mapper.system;

import com.fanyin.common.enums.MenuClassify;
import com.fanyin.model.system.SystemMenu;
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
     * 获取某用户的菜单列表 根据菜单分类
     * @param operatorId 用户id
     * @param classify   菜单分类
     * @return 用户所有可查看菜单列表
     */
    List<SystemMenu> getMenuList(@Param("operatorId") Integer operatorId, @Param("classify") MenuClassify classify);

    /**
     * 根据nid与pid查询菜单
     * @param nid nid
     * @param pid pid
     * @return 菜单 默认只查一条
     */
    SystemMenu getByNid(@Param("nid") String nid, @Param("pid") Integer pid);
}