package com.eghm.domain.system.repository;

import com.eghm.domain.system.model.SysConfig;

/**
 * 系统配置仓储
 *
 * @author 二哥很猛
 */
public interface SysConfigRepository {

    /**
     * 根据id查询配置
     *
     * @param id 主键
     * @return 配置
     */
    SysConfig findById(Long id);

    /**
     * 更新配置内容
     *
     * @param config 配置
     */
    void update(SysConfig config);
}
