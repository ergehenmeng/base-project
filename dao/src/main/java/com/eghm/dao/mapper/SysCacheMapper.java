package com.eghm.dao.mapper;

import com.eghm.dao.model.SysCache;

import java.util.List;

/**
 * 仅做系统管理
 * @author 二哥很猛
 */
public interface SysCacheMapper {

    /**
     * 插入不为空的记录
     *
     * @param record
     */
    int insertSelective(SysCache record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SysCache selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SysCache record);

    /**
     * 根据cacheName更新缓存信息
     * @param cache cache信息
     * @return 更新条数
     */
    int updateCache(SysCache cache);

    /**
     * 获取所有的缓存信息
     * @return 缓存列表
     */
    List<SysCache> getList();
}