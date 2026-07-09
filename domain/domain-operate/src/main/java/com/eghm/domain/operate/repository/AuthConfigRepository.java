package com.eghm.domain.operate.repository;

import com.eghm.domain.operate.model.AuthConfig;

/**
 * 第三方授权配置仓储接口
 *
 * @author 二哥很猛
 * @since 2023/10/20
 */
public interface AuthConfigRepository {

    /**
     * 保存第三方授权配置
     *
     * @param authConfig 第三方授权配置
     */
    void save(AuthConfig authConfig);

    /**
     * 更新第三方授权配置
     *
     * @param authConfig 第三方授权配置
     */
    void update(AuthConfig authConfig);

    /**
     * 删除授权信息
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 根据id查询
     *
     * @param id id
     * @return 配置信息
     */
    AuthConfig findById(Long id);

    /**
     * 判断单位名称是否重复
     *
     * @param title     单位名称
     * @param excludeId 排除id
     * @return true:重复
     */
    boolean existsByTitle(String title, Long excludeId);
}
