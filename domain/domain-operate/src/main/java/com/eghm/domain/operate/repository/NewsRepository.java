package com.eghm.domain.operate.repository;

import com.eghm.domain.operate.model.News;

/**
 * 资讯仓储接口
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
public interface NewsRepository {

    /**
     * 根据id查询资讯
     *
     * @param id id
     * @return 资讯信息
     */
    News findById(Long id);

    /**
     * 保存资讯
     *
     * @param news 资讯信息
     */
    void save(News news);

    /**
     * 更新资讯
     *
     * @param news 资讯信息
     */
    void update(News news);

    /**
     * 删除资讯
     *
     * @param id 资讯id
     */
    void deleteById(Long id);

    /**
     * 判断同编码下资讯标题是否重复
     *
     * @param title     标题
     * @param code      编码
     * @param excludeId 排除id
     * @return true:重复
     */
    boolean existsByTitleAndCode(String title, String code, Long excludeId);

    /**
     * 更新状态
     *
     * @param id    主键
     * @param state 是否显示
     */
    void updateState(Long id, Boolean state);

    /**
     * 排序
     *
     * @param id     id
     * @param sortBy 排序 最大999
     */
    void updateSort(Long id, Integer sortBy);

    /**
     * 更新点赞数量
     *
     * @param id    id
     * @param delta 点赞数量增量
     */
    void updatePraiseNum(Long id, Integer delta);
}
