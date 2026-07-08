package com.eghm.operate.repository;

import com.eghm.operate.model.NewsConfig;

/**
 * 资讯配置仓储接口
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
public interface NewsConfigRepository {

    /**
     * 保存资讯配置
     *
     * @param newsConfig 资讯配置
     */
    void save(NewsConfig newsConfig);

    /**
     * 更新资讯配置
     *
     * @param newsConfig 资讯配置
     */
    void update(NewsConfig newsConfig);

    /**
     * 删除资讯配置
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 配置信息
     */
    NewsConfig findByCode(String code);

    /**
     * 判断标题是否重复
     *
     * @param title     标题
     * @param excludeId 排除id
     * @return true:重复
     */
    boolean existsByTitle(String title, Long excludeId);

    /**
     * 判断编码是否重复
     *
     * @param code      编码
     * @param excludeId 排除id
     * @return true:重复
     */
    boolean existsByCode(String code, Long excludeId);
}
