package com.eghm.operate.repository;

import com.eghm.operate.model.Banner;

/**
 * 轮播图仓储接口
 *
 * @author 二哥很猛
 */
public interface BannerRepository {

    /**
     * 保存轮播图信息
     *
     * @param banner 轮播图信息
     */
    void save(Banner banner);

    /**
     * 更新轮播图信息
     *
     * @param banner 轮播图信息
     */
    void update(Banner banner);

    /**
     * 删除轮播图信息
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 更新排序
     *
     * @param id   主键
     * @param sort 1~999
     */
    void updateSort(Long id, Integer sort);

    /**
     * 更新状态
     *
     * @param id    主键
     * @param state 是否可点击
     */
    void updateState(Long id, Boolean state);
}
