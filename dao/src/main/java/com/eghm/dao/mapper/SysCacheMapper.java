package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.SysCache;

import java.util.List;

/**
 * 仅做系统管理
 * @author 二哥很猛
 */
public interface SysCacheMapper extends BaseMapper<SysCache> {

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