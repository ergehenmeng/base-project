package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.po.SysCachePO;

/**
 * 仅做系统管理
 *
 * @author 二哥很猛
 */
public interface SysCacheMapper extends BaseMapper<SysCachePO> {

    /**
     * 根据cacheName更新缓存信息
     *
     * @param cache cache信息
     */
    void updateCache(SysCachePO cache);

}
