package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.SysCache;

/**
 * 仅做系统管理
 *
 * @author 二哥很猛
 */
public interface SysCacheMapper extends BaseMapper<SysCache> {

    /**
     * 根据cacheName更新缓存信息
     *
     * @param cache cache信息
     * @return 更新条数
     */
    int updateCache(SysCache cache);

}